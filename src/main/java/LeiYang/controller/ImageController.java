package LeiYang.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import LeiYang.entity.Image;
import LeiYang.dao.ImageDao;
import LeiYang.entity.Product;
import LeiYang.service.CloudWatchService;
import LeiYang.service.ProductService;
import LeiYang.service.UserService;
import LeiYang.util.ExceptionMessage;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.timgroup.statsd.StatsDClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Resource;

@RestController
@RequestMapping("/v1")
public class ImageController {
    private static final Logger logger = LogManager.getLogger(ImageController.class);
    @Autowired
    private StatsDClient statsDClient;
    @Resource
    private ImageDao imageDao;
    @Resource
    private UserService userService;

    @Autowired
    private CloudWatchService cloudWatchService;

    @Resource
    ProductService productService;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Value("${aws.region}")
    private String awsRegion;

    private AmazonS3 s3client;

    //调用withCredentials()方法将一个InstanceProfileCredentialsProvider对象传递给AmazonS3ClientBuilder对象。
    //InstanceProfileCredentialsProvider从Amazon EC2实例元数据服务中获取IAM角色的凭证
    public ImageController() {
        this.s3client = AmazonS3ClientBuilder.standard()
                .withCredentials(new InstanceProfileCredentialsProvider(false))
                .withRegion(awsRegion)
                .build();
    }

    @PostMapping(value = "/product/{product_id}/image")
    public Object uploadProductImage(@PathVariable("product_id") Long productId,
                                     @RequestParam("file") MultipartFile file) throws IOException {
        long startTime = System.currentTimeMillis();
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Object principal = authentication.getPrincipal();
        String userName = ((UserDetails) principal).getUsername();
        long id = userService.getId(userName);
        if(id != productService.findOwnerId(productId)){
            statsDClient.incrementCounter("ImageUploadedFail");
            long responseTime = System.currentTimeMillis() - startTime;
            logger.info("uploadProductImage failed: user with ID {} is not the owner of product with ID {}", id, productId);
            cloudWatchService.sendCustomMetric("ImageUploadedFail", 1, responseTime);
            return new ExceptionMessage().fail();
        }
        // Get file extension
        String extension = getFileExtension(file);
        // Generate unique file name
        String fileName = UUID.randomUUID().toString() + "." + extension;
        // Create file in temporary directory
        File tempFile = Files.createTempFile(fileName, extension).toFile();
        file.transferTo(tempFile);
        // Upload file to S3 bucket
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, productId + "/" + fileName, tempFile)
                .withCannedAcl(CannedAccessControlList.PublicRead);
        s3client.putObject(putObjectRequest);
        // Delete temporary file
        tempFile.delete();
        // Generate URL for uploaded file
        String fileUrl = s3client.getUrl(bucketName, productId + "/" + fileName).toString();
        // store the image information in RDS
        Image image = new Image(productId,fileName,fileUrl);
        imageDao.save(image);
        long responseTime = System.currentTimeMillis() - startTime;
        statsDClient.incrementCounter("ImageUploaded");
        cloudWatchService.sendCustomMetric("ImageUploaded", 1, responseTime);
        logger.info("uploadProductImage succeed");
        return ResponseEntity.status(HttpStatus.OK).body(image);
    }

    @GetMapping(value = "/product/{product_id}/image")
    public ResponseEntity<List> getProductImageList(@PathVariable("product_id") Long productId) throws IOException {
        long startTime = System.currentTimeMillis();
        if (productService.findById(productId) == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Arrays.asList("404, No such product"));
        long responseTime = System.currentTimeMillis() - startTime;
        statsDClient.incrementCounter("GetProductImageList succeed");
        cloudWatchService.sendCustomMetric("GetProductImageList", 1, responseTime);
        logger.info("GetProductImageList succeed");
        return ResponseEntity.status(HttpStatus.OK).body(imageDao.findByProductId(productId));
    }

    @GetMapping(value = "/product/{product_id}/image/{image_id}")
    public Object getProductImage(@PathVariable("product_id") Long productId,
                                  @PathVariable("image_id") Long imageId) throws IOException {
        long startTime = System.currentTimeMillis();
        if (productService.findById(productId) == null) return new ExceptionMessage().fail();
        statsDClient.incrementCounter("GetProductImage succeed");
        long responseTime = System.currentTimeMillis() - startTime;
        cloudWatchService.sendCustomMetric("GetProductImage", 1, responseTime);
        logger.info("GetProductImage succeed");
        return  ResponseEntity.status(HttpStatus.OK).body(imageDao.findByDoubleId(imageId,productId));
    }

    @DeleteMapping(value = "/product/{product_id}/image/{image_id}")
    public ExceptionMessage getProductImageList(@PathVariable("product_id") Long productId,
                                                @PathVariable("image_id") Long imageId) throws IOException {
        long startTime = System.currentTimeMillis();
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Object principal = authentication.getPrincipal();
        String userName = ((UserDetails) principal).getUsername();
        long id = userService.getId(userName);
        if(id != productService.findOwnerId(productId)){
            return new ExceptionMessage().fail();
        }
        //delete the file from S3 bucket
        String fileName = imageDao.findsFileName(imageId);
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucketName, productId + "/" + fileName);
        s3client.deleteObject(deleteObjectRequest);
        // delete the file from database
        imageDao.delete(imageId);
        statsDClient.incrementCounter("ImageDeleted succeed");
        long responseTime = System.currentTimeMillis() - startTime;
        cloudWatchService.sendCustomMetric("ImageDeleted", 1, responseTime);
        logger.info("ImageDeleted succeed");
        return new ExceptionMessage().success();
    }

    private String getFileExtension(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        return originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
    }

}

package LeiYang.controller;

import LeiYang.entity.Product;
import LeiYang.entity.ProductVo;
import LeiYang.service.CloudWatchService;
import LeiYang.service.ProductService;
import LeiYang.service.UserService;
import LeiYang.util.ExceptionMessage;
import com.timgroup.statsd.StatsDClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Resource;
import java.security.Principal;

@RestController
public class ProductController {
    private static final Logger logger = LogManager.getLogger(ProductController.class);
    @Autowired
    private StatsDClient statsDClient;
    @Resource
    private ProductService productService;
    @Resource
    private UserService userService;
    @Autowired
    private CloudWatchService cloudWatchService;
    @PostMapping("/v1/product")
    public Object add(@RequestBody ProductVo productVo){
        long startTime = System.currentTimeMillis();
        if(productService.find(productVo.getSku()) != null){
            statsDClient.incrementCounter("product.created.failed.count");
            long responseTime = System.currentTimeMillis() - startTime;
            statsDClient.recordExecutionTime("product.created.failed.time", responseTime);
            logger.info("ProductCreationFailed");
            cloudWatchService.sendCustomMetric("ProductCreationFailed", 1, responseTime);
            return new ExceptionMessage().fail();
        }
        else{
            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = context.getAuthentication();
            Object principal = authentication.getPrincipal();
            String userName = ((UserDetails) principal).getUsername();
            long id = userService.getId(userName);
            Product product = new Product(productVo.getName(), productVo.getDescription(), productVo.getSku(),productVo.getManufacturer(),productVo.getQuantity(), id);
            productService.save(product);
            statsDClient.incrementCounter("product.created.succeed.count");
            long responseTime = System.currentTimeMillis() - startTime;
            statsDClient.recordExecutionTime("product.created.succeed.time", responseTime);
            cloudWatchService.sendCustomMetric("ProductCreated", 1, responseTime);
            logger.info("ProductCreated");
            return productService.findTheLastOne();
        }
    }

    @PutMapping("/v1/product/{productId}")
    public ExceptionMessage updatePut(@RequestBody ProductVo productVo,@PathVariable Long productId){
        long startTime = System.currentTimeMillis();
        Product existingProduct = productService.findById(productId);
        if (existingProduct == null) {
            long responseTime = System.currentTimeMillis() - startTime;
            statsDClient.incrementCounter("product.updated.failed.count");
            cloudWatchService.sendCustomMetric("ProductUpdateFailed", 1, responseTime);
            statsDClient.recordExecutionTime("product.updated.failed.time", responseTime);
            logger.info("ProductUpdate Failed");
            return new ExceptionMessage().fail();
        }
        productService.update(productVo.getName(), productVo.getDescription(), productVo.getSku(), productVo.getManufacturer(), productVo.getQuantity(), productId);
        long responseTime = System.currentTimeMillis() - startTime;
        statsDClient.incrementCounter("product.updated.succeed.count");
        cloudWatchService.sendCustomMetric("ProductUpdated", 1, responseTime);
        statsDClient.recordExecutionTime("product.updated.succeed.time", responseTime);
        logger.info("ProductUpdated Succeed");
        return new ExceptionMessage().success();
    }

    @PatchMapping("/v1/product/{productId}")
    public ExceptionMessage updatePatch(@RequestBody ProductVo productVo,@PathVariable Long productId){
        long startTime = System.currentTimeMillis();
        Product existingProduct = productService.findById(productId);
        if (existingProduct == null) {
            statsDClient.incrementCounter("product.updated.patch.failed.count");
            long responseTime = System.currentTimeMillis() - startTime;
            statsDClient.recordExecutionTime("product.updated.patch.failed.time", responseTime);
            cloudWatchService.sendCustomMetric("ProductUpdateFailed(Patch)", 1, responseTime);
            logger.info("ProductUpdate Failed(Patch Method)");
            return new ExceptionMessage().fail();
        }
        boolean isUpdated = false;
        if (productVo.getName() != null) {
            existingProduct.setName(productVo.getName());
            isUpdated = true;
        }
        if (productVo.getDescription() != null) {
            existingProduct.setDescription(productVo.getDescription());
            isUpdated = true;
        }
        if (productVo.getSku() != null) {
            existingProduct.setSku(productVo.getSku());
            isUpdated = true;
        }
        if (productVo.getManufacturer() != null) {
            existingProduct.setManufacturer(productVo.getManufacturer());
            isUpdated = true;
        }
        if (productVo.getQuantity() != null) {
            existingProduct.setQuantity(productVo.getQuantity());
            isUpdated = true;
        }
        if (isUpdated) {
            //productService.update(existingProduct);
            productService.update(existingProduct.getName(), existingProduct.getDescription(), existingProduct.getSku(), existingProduct.getManufacturer(), existingProduct.getQuantity(), productId);
            statsDClient.incrementCounter("product.updated.patch.succeed.count");
            long responseTime = System.currentTimeMillis() - startTime;
            statsDClient.recordExecutionTime("product.updated.patch.succeed.time", responseTime);
            cloudWatchService.sendCustomMetric("ProductUpdated(Patch)", 1, responseTime);
            logger.info("ProductUpdated(Patch) succeed");
            return new ExceptionMessage().success();
        } else {
            productService.update(existingProduct.getName(), existingProduct.getDescription(), existingProduct.getSku(), existingProduct.getManufacturer(), existingProduct.getQuantity(), productId);
            long responseTime = System.currentTimeMillis() - startTime;
            statsDClient.recordExecutionTime("product.updated.patch.failed.time", responseTime);
            statsDClient.incrementCounter("product.updated.patch.failed.count");
            cloudWatchService.sendCustomMetric("ProductUpdateFailed(Patch)", 1, responseTime);
            logger.info("ProductUpdated(Patch) failed");
            return new ExceptionMessage().fail();
        }
    }

    @DeleteMapping("/v1/product/{productId}")
    public ExceptionMessage delete(@PathVariable Long productId){
        long startTime = System.currentTimeMillis();
        ExceptionMessage result = productService.delete(productId);
        if (result.equals(new ExceptionMessage().success())) {
            statsDClient.incrementCounter("product.deleted.succeed.count");
            long responseTime = System.currentTimeMillis() - startTime;
            statsDClient.recordExecutionTime("product.deleted.succeed.time", responseTime);
            cloudWatchService.sendCustomMetric("ProductDeleted", 1, responseTime);
            logger.info("ProductDeleted succeed");
        } else {
            statsDClient.incrementCounter("product.deleted.failed.count");
            long responseTime = System.currentTimeMillis() - startTime;
            statsDClient.recordExecutionTime("product.deleted.failed.time", responseTime);
            cloudWatchService.sendCustomMetric("ProductDeleteFailed", 1, responseTime);
            logger.info("ProductDelete Failed");
        }
        return result;
    }

    @GetMapping("/v2/product/{productId}")
    public Product getProduct(@PathVariable Long productId){
        long startTime = System.currentTimeMillis();
        Product product = productService.findById(productId);
        if (product == null) {
            statsDClient.incrementCounter("product.not.found.count");
            long responseTime = System.currentTimeMillis() - startTime;
            statsDClient.recordExecutionTime("product.not.found.time", responseTime);
            cloudWatchService.sendCustomMetric("ProductNotFound", 1, responseTime);
            logger.info("ProductNotFound");
        } else {
            statsDClient.incrementCounter("product.got.succeed.count");
            long responseTime = System.currentTimeMillis() - startTime;
            statsDClient.recordExecutionTime("product.got.succeed.time", responseTime);
            cloudWatchService.sendCustomMetric("ProductFound", 1, responseTime);
            logger.info("ProductFound");
        }
        return product;
    }

}

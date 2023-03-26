package LeiYang.controller;

import LeiYang.entity.Product;
import LeiYang.entity.ProductVo;
import LeiYang.service.CloudWatchService;
import LeiYang.service.ProductService;
import LeiYang.service.UserService;
import LeiYang.util.ExceptionMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;

@RestController
public class ProductController {
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
            long responseTime = System.currentTimeMillis() - startTime;
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
            long responseTime = System.currentTimeMillis() - startTime;
            cloudWatchService.sendCustomMetric("ProductCreated", 1, responseTime);
            return productService.findTheLastOne();
        }
    }

    @PutMapping("/v1/product/{productId}")
    public ExceptionMessage updatePut(@RequestBody ProductVo productVo,@PathVariable Long productId){
        long startTime = System.currentTimeMillis();
        Product existingProduct = productService.findById(productId);
        if (existingProduct == null) {
            long responseTime = System.currentTimeMillis() - startTime;
            cloudWatchService.sendCustomMetric("ProductUpdateFailed", 1, responseTime);
            return new ExceptionMessage().fail();
        }
        productService.update(productVo.getName(), productVo.getDescription(), productVo.getSku(), productVo.getManufacturer(), productVo.getQuantity(), productId);
        long responseTime = System.currentTimeMillis() - startTime;
        cloudWatchService.sendCustomMetric("ProductUpdated", 1, responseTime);
        return new ExceptionMessage().success();
    }

    @PatchMapping("/v1/product/{productId}")
    public ExceptionMessage updatePatch(@RequestBody ProductVo productVo,@PathVariable Long productId){
        long startTime = System.currentTimeMillis();
        Product existingProduct = productService.findById(productId);
        if (existingProduct == null) {
            long responseTime = System.currentTimeMillis() - startTime;
            cloudWatchService.sendCustomMetric("ProductUpdateFailed", 1, responseTime);
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
            long responseTime = System.currentTimeMillis() - startTime;
            cloudWatchService.sendCustomMetric("ProductUpdated(Patch)", 1, responseTime);
            return new ExceptionMessage().success();
        } else {
            productService.update(existingProduct.getName(), existingProduct.getDescription(), existingProduct.getSku(), existingProduct.getManufacturer(), existingProduct.getQuantity(), productId);
            long responseTime = System.currentTimeMillis() - startTime;
            cloudWatchService.sendCustomMetric("ProductUpdateFailed(Patch)", 1, responseTime);
            return new ExceptionMessage().fail();
        }
    }

    @DeleteMapping("/v1/product/{productId}")
    public ExceptionMessage delete(@PathVariable Long productId){
        long startTime = System.currentTimeMillis();
        ExceptionMessage result = productService.delete(productId);
        if (result.equals(new ExceptionMessage().success())) {
            long responseTime = System.currentTimeMillis() - startTime;
            cloudWatchService.sendCustomMetric("ProductDeleted", 1, responseTime);
        } else {
            long responseTime = System.currentTimeMillis() - startTime;
            cloudWatchService.sendCustomMetric("ProductDeleteFailed", 1, responseTime);
        }
        return result;
    }

    @GetMapping("/v1/product/{productId}")
    public Product getProduct(@PathVariable Long productId){
        long startTime = System.currentTimeMillis();
        Product product = productService.findById(productId);
        if (product == null) {
            long responseTime = System.currentTimeMillis() - startTime;
            cloudWatchService.sendCustomMetric("ProductNotFound", 1, responseTime);
        } else {
            long responseTime = System.currentTimeMillis() - startTime;
            cloudWatchService.sendCustomMetric("ProductFound", 1, responseTime);
        }
        return product;
    }

}

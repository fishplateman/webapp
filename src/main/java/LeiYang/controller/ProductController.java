package LeiYang.controller;

import LeiYang.entity.Product;
import LeiYang.entity.ProductVo;
import LeiYang.service.ProductService;
import LeiYang.service.UserService;
import LeiYang.util.ExceptionMessage;
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
    @PostMapping("/v1/product")
    public Object add(@RequestBody ProductVo productVo){
        if(productService.find(productVo.getSku()) != null){
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
            return productService.findTheLastOne();
        }
    }

    @PutMapping("/v1/product/{productId}")
    public ExceptionMessage updatePut(@RequestBody ProductVo productVo,@PathVariable Long productId){
        productService.update(productVo.getName(), productVo.getDescription(), productVo.getSku(), productVo.getManufacturer(), productVo.getQuantity(), productId);
        return new ExceptionMessage().success();
    }

    @PatchMapping("/v1/product/{productId}")
    public ExceptionMessage updatePatch(@RequestBody ProductVo productVo,@PathVariable Long productId){
        productService.update(productVo.getName(), productVo.getDescription(), productVo.getSku(), productVo.getManufacturer(), productVo.getQuantity(), productId);
        return new ExceptionMessage().success();
    }

    @DeleteMapping("/v1/product/{productId}")
    public ExceptionMessage delete(@PathVariable Long productId){
        return productService.delete(productId);
    }

    @GetMapping("/v1/product/{productId}")
    public Product getProduct(@PathVariable Long productId){
        Product product = productService.findById(productId);
        return product;
    }

}

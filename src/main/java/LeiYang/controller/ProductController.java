package LeiYang.controller;

import LeiYang.entity.Product;
import LeiYang.entity.ProductVo;
import LeiYang.entity.UserVo;
import LeiYang.entity.Users;
import LeiYang.service.ProductService;
import LeiYang.util.Bycrypt;
import LeiYang.util.EmailVerify;
import LeiYang.util.ExceptionMessage;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class ProductController {
    @Resource
    private ProductService productService;
    @PostMapping("/v1/product")
    public ExceptionMessage add(@RequestBody ProductVo productVo){
        if(productService.find(productVo.getSku()) != null){
            return new ExceptionMessage().fail();
        }
        else{
            Product product = new Product(productVo.getName(), productVo.getDescription(), productVo.getSku(),productVo.getManufacturer(),productVo.getQuantity(), 1L);
            productService.save(product);
            return new ExceptionMessage().success();
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
        productService.delete(productId);
        return new ExceptionMessage().success();
    }

    @GetMapping("/v1/product/{productId}")
    public Product getProduct(@PathVariable Long productId){
        Product product = productService.findById(productId);
        return product;
    }

}

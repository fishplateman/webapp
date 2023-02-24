package LeiYang.service;

import LeiYang.dao.ProductDao;
import LeiYang.entity.Product;
import LeiYang.entity.Users;
import LeiYang.util.ExceptionMessage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ProductService {
    @Resource
    private ProductDao productDao;

    public void save(Product product){
        productDao.save(product);
    }

    public Product findTheLastOne(){
        return productDao.findTheLastOne();
    }
    public Product find(String sku){
        Product product = productDao.findBySku(sku);
        return product;
    }

    public void update(String name, String description, String sku, String manufacturer, int quantity, Long id) {
        productDao.update(name, description, sku, manufacturer, quantity, id);
    }

    public ExceptionMessage delete(Long id){
        if(productDao.findById(id) != null){
            productDao.delete(id);
            return new ExceptionMessage().success();
        }
        return new ExceptionMessage().noResource();

    }

    public Product findById(long id){
        Product product = productDao.findOne(id);
        return product;
    }
}

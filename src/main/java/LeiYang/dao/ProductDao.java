package LeiYang.dao;

import LeiYang.entity.Product;
import LeiYang.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductDao extends JpaRepository<Product,Long> {
    @Query(value = "SELECT *FROM product where sku = ?1", nativeQuery = true)
    Product findBySku(String sku);

    @Modifying
    @Transactional
    @Query(value = "UPDATE product SET name = ?1 , description = ?2 , sku = ?3 , manufacturer = ?4 , quantity = ?5 WHERE id = ?6", nativeQuery = true)
    int update(String name, String description, String sku, String manufacturer, int quantity, long id);

}

package LeiYang.dao;

import LeiYang.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdectDao extends JpaRepository<Product,Long> {

}

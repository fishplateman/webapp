package LeiYang.dao;

import LeiYang.entity.Image;
import LeiYang.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageDao extends JpaRepository<Image,Long> {
    List<Image> findByProductId(Long productId);

    @Query(value = "SELECT *FROM image where image_id = ?1 AND product_id = ?2", nativeQuery = true)
    Image findByDoubleId(long image_id, long product_id);

    @Query(value = "select s3_bucket_path from image where image_id = ?1", nativeQuery = true)
    String finds3_bucket_path(long image_id);
}

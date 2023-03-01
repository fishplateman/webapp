package LeiYang.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Table(name = "image")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Image {
    public Image() {
    }
    public Image(Long productId, String fileName, String s3_bucket_path) {
        this.productId = productId;
        this.fileName = fileName;
        this.s3_bucket_path = s3_bucket_path;
    }
    @Id
    @Column(name = "image_id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long image_id;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "s3_bucket_path", nullable = false)
    private String s3_bucket_path;

    @CreatedDate
    @Column(name = "date_added")
    private Date dateAdded;


    public Long getId() {
        return image_id;
    }

    public void setId(Long id) {
        this.image_id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

}

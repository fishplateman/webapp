package LeiYang.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Table(name = "product")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Product {

    public Product() {
    }

    public Product(String name, String description, String sku, String manufacturer, int quantity, Long ownerUserId) {
        this.name = name;
        this.description = description;
        this.sku = sku;
        this.manufacturer = manufacturer;
        this.quantity = quantity;
        this.ownerUserId = ownerUserId;
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "sku")
    private String sku;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "quantity")
    private int quantity;

    @CreatedDate
    @Column(name = "date_added")
    private Date dateAdded;

    @LastModifiedDate
    @Column(name = "date_last_updated")
    private Date dateUpdated;

    @Column(name = "owner_user_id", nullable = false)
    private Long ownerUserId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserId() {
        return ownerUserId;
    }

    public void setUserId(Long userId) {
        this.ownerUserId = userId;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

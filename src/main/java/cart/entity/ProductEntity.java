package cart.entity;

import cart.dto.request.ProductRequest;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ProductEntity {
    private final Long id;
    private String name;
    private String image;
    private Integer price;
    private final Timestamp createdAt;
    private Timestamp updatedAt;

    public ProductEntity(Long id, String name, String image, Integer price, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static ProductEntity create(Long id, String name, String image, Integer price) {
        Timestamp currentTime = Timestamp.valueOf(LocalDateTime.now());
        return new ProductEntity(id, name, image, price, currentTime, null);
    }

    public void replace(ProductRequest productRequest) {
        this.name = productRequest.getName();
        this.image = productRequest.getImage();
        this.price = productRequest.getPrice();
        this.updatedAt = Timestamp.valueOf(LocalDateTime.now());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public Integer getPrice() {
        return price;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
}

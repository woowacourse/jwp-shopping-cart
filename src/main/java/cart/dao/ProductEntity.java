package cart.dao;

import java.math.BigDecimal;

public class ProductEntity {

    private final Long id;
    private final String name;
    private final String imageUrl;
    private final BigDecimal price;

    public ProductEntity(Long id, String name, String imageUrl, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }
}

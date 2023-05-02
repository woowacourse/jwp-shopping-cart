package cart.dao;

import cart.domain.product.Product;
import java.util.Objects;

public class ProductEntity {

    private final Long id;
    private final String name;
    private final Integer price;
    private final String category;
    private final String imageUrl;

    public ProductEntity(Long id, String name, Integer price, String category, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    public ProductEntity(Product product) {
        this.id = product.getProductId();
        this.name = product.getName();
        this.price = product.getPrice().intValue();
        this.category = product.getCategory().name();
        this.imageUrl = product.getImageUrl();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductEntity that = (ProductEntity) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}

package cart.persistence.entity;

import cart.domain.product.ProductImageUrl;
import cart.domain.product.ProductName;
import cart.domain.product.ProductPrice;
import java.util.Objects;

public class ProductEntity {

    private final Long id;
    private final ProductName name;
    private final ProductPrice price;
    private final ProductImageUrl imageUrl;

    private ProductEntity(Long id, ProductName name, ProductPrice price, ProductImageUrl imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public static ProductEntity create(Long id, String name, long price, String imageUrl) {
        return new ProductEntity(
                id,
                new ProductName(name),
                new ProductPrice(price),
                new ProductImageUrl(imageUrl));
    }

    public static ProductEntity createToSave(String name, long price, String imageUrl) {
        return create(null, name, price, imageUrl);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

    public long getPrice() {
        return price.getAmount();
    }

    public String getImageUrl() {
        return imageUrl.getUrl();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductEntity productEntity = (ProductEntity) o;
        return Objects.equals(id, productEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

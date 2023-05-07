package cart.domain.product;

import java.util.Objects;

public class ProductEntity {

    private final ProductId id;
    private final Product product;

    public ProductEntity(final long id, final String name, final int price, final String imageUrl) {
        this.id = new ProductId(id);
        this.product = new Product(name, price, imageUrl);
    }

    public long getId() {
        return id.getValue();
    }

    public String getName() {
        return product.getName();
    }

    public int getPrice() {
        return product.getPrice();
    }

    public String getImageUrl() {
        return product.getImageUrl();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductEntity product = (ProductEntity) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

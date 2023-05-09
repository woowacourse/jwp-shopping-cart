package cart.dto.application;

import cart.dto.request.ProductRequest;
import java.util.Objects;

public class ProductEntityDto {

    private final long id;
    private final ProductDto product;

    public ProductEntityDto(final long id, final String name, final int price, final String imageUrl) {
        this.id = id;
        this.product = new ProductDto(name, price, imageUrl);
    }

    public ProductEntityDto(final long id, final ProductDto product) {
        this.id = id;
        this.product = product;
    }

    public ProductEntityDto(final long id, final ProductRequest product) {
        this.id = id;
        this.product = new ProductDto(product);
    }

    public long getId() {
        return id;
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

    public ProductDto getProduct() {
        return product;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductEntityDto that = (ProductEntityDto) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

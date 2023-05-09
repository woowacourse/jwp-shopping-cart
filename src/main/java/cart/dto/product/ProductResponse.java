package cart.dto.product;

import cart.domain.product.Product;
import java.util.Objects;

public class ProductResponse {

    private final long productId;
    private final String name;
    private final String image;
    private final int price;

    public ProductResponse(final long productId, final String name, final String image, final int price) {
        this.productId = productId;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public static ProductResponse from(final Product product) {
        return new ProductResponse(
                product.getProductId().getValue(),
                product.getProductName(),
                product.getProductImage().getValue(),
                product.getProductPrice().getValue()
        );
    }

    public long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductResponse that = (ProductResponse) o;
        return productId == that.productId && price == that.price && Objects.equals(name, that.name) && Objects.equals(
                image,
                that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, name, image, price);
    }
}

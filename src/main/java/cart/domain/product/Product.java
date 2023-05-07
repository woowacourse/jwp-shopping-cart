package cart.domain.product;

import java.util.Objects;

public class Product {

    private final ProductName name;
    private final ProductPrice price;
    private final ProductImageUrl imageUrl;

    public Product(final String name, final int price, final String imageUrl) {
        this.name = new ProductName(name);
        this.price = new ProductPrice(price);
        this.imageUrl = new ProductImageUrl(imageUrl);
    }

    public String getName() {
        return name.getValue();
    }

    public int getPrice() {
        return price.getValue();
    }

    public String getImageUrl() {
        return imageUrl.getValue();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Product product = (Product) o;
        return Objects.equals(name, product.name) && Objects.equals(price, product.price)
                && Objects.equals(imageUrl, product.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, imageUrl);
    }
}

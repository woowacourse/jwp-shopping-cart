package cart.entity;

import cart.exception.ImageUrlExtensionNotValidException;
import cart.exception.NegativePriceException;
import cart.exception.ProductNameLengthOverException;

public class Product {

    private static final int MAX_NAME_LENGTH = 255;
    private static final int MIN_NAME_LENGTH = 0;
    private static final String IMAGE_EXTENSION_FORMAT = ".*\\.(jpg|jpeg|png|webp|avif|gif|svg)$";

    private final Long id;
    private final String name;
    private final String imageUrl;
    private final Integer price;

    public Product(final Long id, final String name, final String imageUrl, final Integer price) {
        validateParameter(name, imageUrl, price);
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    private void validateParameter(final String name, final String imageUrl, final Integer price) {
        validateImageUrl(imageUrl);
        validatePrice(price);
        validateName(name);
    }

    public Product(final String name, final String imageUrl, final Integer price) {
        this(null, name, imageUrl, price);
    }

    private void validateName(final String name) {
        if (name.length() > MAX_NAME_LENGTH || name.length() == MIN_NAME_LENGTH) {
            throw new ProductNameLengthOverException();
        }
    }

    private void validatePrice(final int price) {
        if (price < 0) {
            throw new NegativePriceException();
        }
    }

    private void validateImageUrl(final String imageUrl) {
        if (!imageUrl.matches(IMAGE_EXTENSION_FORMAT)) {
            throw new ImageUrlExtensionNotValidException();
        }
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

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", price=" + price +
                '}';
    }
}

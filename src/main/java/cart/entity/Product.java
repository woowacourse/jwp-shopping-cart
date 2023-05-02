package cart.entity;

import cart.exception.product.NullOrBlankException;
import cart.exception.product.PriceNotUnderZeroException;

public class Product {

    private final Long id;
    private final String name;
    private final String imageUrl;
    private final int price;

    public Product(final Long id, final String name, final String imageUrl, final int price) {
        validateNullOrBlank(name, imageUrl);
        validatePriceValue(price);
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public Product(final String name, final String imageUrl, final int price) {
        this(null, name, imageUrl, price);
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

    private void validateNullOrBlank(String name, String imageUrl) {
        if (name == null || imageUrl == null || name.isBlank() || imageUrl.isBlank()) {
            throw new NullOrBlankException();
        }
    }

    private void validatePriceValue(int price) {
        if (price < 0) {
            throw new PriceNotUnderZeroException();
        }
    }
}

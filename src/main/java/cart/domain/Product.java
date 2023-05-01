package cart.domain;

import static cart.exception.ErrorCode.PRODUCT_NAME_LENGTH;
import static cart.exception.ErrorCode.PRODUCT_PRICE_RANGE;

import cart.exception.GlobalException;

public class Product {

    private static final int NAME_MIN_LENGTH = 1, NAME_MAX_LENGTH = 25;
    private static final int PRICE_MIN_RANGE = 0, PRICE_MAX_RANGE = 10_000_000;

    private final String name;
    private final String imageUrl;
    private final int price;
    private final ProductCategory category;

    private Product(final String name, final String imageUrl, final int price,
                    final ProductCategory category) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.category = category;
    }

    public static Product create(final String name, final String imageUrl, final int price,
                                 final ProductCategory category) {
        validateNameLength(name);
        validatePrice(price);
        return new Product(name, imageUrl, price, category);
    }

    private static void validateNameLength(final String name) {
        if (name.length() < NAME_MIN_LENGTH || name.length() > NAME_MAX_LENGTH) {
            throw new GlobalException(PRODUCT_NAME_LENGTH);
        }
    }

    private static void validatePrice(final int price) {
        if (price < PRICE_MIN_RANGE || price > PRICE_MAX_RANGE) {
            throw new GlobalException(PRODUCT_PRICE_RANGE);
        }
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

    public ProductCategory getCategory() {
        return category;
    }
}

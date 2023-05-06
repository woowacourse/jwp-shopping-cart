package cart.domain;

import static cart.exception.ErrorCode.PRODUCT_NAME_LENGTH;

import cart.exception.GlobalException;

public class ProductName {

    private static final int NAME_MIN_LENGTH = 1, NAME_MAX_LENGTH = 25;

    private final String name;

    private ProductName(final String name) {
        this.name = name;
    }

    public static ProductName create(final String name) {
        validateNameLength(name);
        return new ProductName(name);
    }

    private static void validateNameLength(final String name) {
        if (name.length() < NAME_MIN_LENGTH || name.length() > NAME_MAX_LENGTH) {
            throw new GlobalException(PRODUCT_NAME_LENGTH);
        }
    }

    public String getName() {
        return name;
    }
}

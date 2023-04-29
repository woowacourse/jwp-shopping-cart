package cart.domain;

import cart.exception.GlobalException;

import static cart.exception.ErrorCode.PRODUCT_INVALID_CATEGORY;

public enum ProductCategory {
    KOREAN, JAPANESE, CHINESE, WESTERN, SNACK, DESSERT;

    public static ProductCategory from(final String category) {
        try {
            return ProductCategory.valueOf(category);
        } catch (final IllegalArgumentException e) {
            throw new GlobalException(PRODUCT_INVALID_CATEGORY);
        }
    }
}

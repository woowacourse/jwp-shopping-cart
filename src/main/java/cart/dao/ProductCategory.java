package cart.dao;

import cart.web.exception.ErrorCode;
import cart.web.exception.GlobalException;

import java.util.Arrays;

public enum ProductCategory {
    KOREAN, JAPANESE, CHINESE, WESTERN, SNACK, DESSERT;

    public static ProductCategory from(final String category) {
        return Arrays.stream(values())
                .filter(value -> value.name().equals(category))
                .findFirst()
                .orElseThrow(() -> new GlobalException(ErrorCode.INVALID_CATEGORY));
    }
}

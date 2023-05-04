package cart.domain.product;

import cart.exception.CategoryNotFoundException;

import java.util.Arrays;

public enum ProductCategory {
    KOREAN, JAPANESE, CHINESE, WESTERN, SNACK, DESSERT;

    public static ProductCategory from(final String category) {
        return Arrays.stream(values())
                .filter(value -> value.name().equals(category))
                .findFirst()
                .orElseThrow(CategoryNotFoundException::new);
    }
}

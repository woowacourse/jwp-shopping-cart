package cart.persistence.entity;

public enum ProductCategory {
    KOREAN, JAPANESE, CHINESE, WESTERN, SNACK, DESSERT;

    public static ProductCategory from(final String category) {
        return ProductCategory.valueOf(category);
    }
}

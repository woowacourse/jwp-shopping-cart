package cart.entity;

public class ProductCategoryEntity {

    private Long productId;
    private Long categoryId;

    public ProductCategoryEntity(final Long productId, final Long categoryId) {
        this.productId = productId;
        this.categoryId = categoryId;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getCategoryId() {
        return categoryId;
    }
}

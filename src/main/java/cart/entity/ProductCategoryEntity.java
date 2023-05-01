package cart.entity;

import java.util.Objects;

public final class ProductCategoryEntity {

    private Long id;
    private Long productId;
    private Long categoryId;

    public ProductCategoryEntity(final Long productId, final Long categoryId) {
        this(null, productId, categoryId);
    }

    public ProductCategoryEntity(final Long id, final Long productId, final Long categoryId) {
        this.id = id;
        this.productId = productId;
        this.categoryId = categoryId;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductCategoryEntity that = (ProductCategoryEntity) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}

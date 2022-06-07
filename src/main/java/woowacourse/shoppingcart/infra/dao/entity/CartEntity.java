package woowacourse.shoppingcart.infra.dao.entity;

public class CartEntity {
    private final Long id;
    private final long customerId;
    private final ProductEntity productEntity;
    private final int quantity;

    public CartEntity(final Long id, final long customerId, final ProductEntity productEntity, final int quantity) {
        this.id = id;
        this.customerId = customerId;
        this.productEntity = productEntity;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public long getCustomerId() {
        return customerId;
    }

    public ProductEntity getProductEntity() {
        return productEntity;
    }

    public int getQuantity() {
        return quantity;
    }
}

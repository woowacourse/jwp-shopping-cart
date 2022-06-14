package woowacourse.shoppingcart.entity;

public class CartItemEntity {

    private final Long id;
    private final Long productId;
    private final int quantity;

    public CartItemEntity(Long id, Long productId, int quantity) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}

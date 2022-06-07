package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.exception.InvalidInputException;

public class CartItem {

    private final Long id;
    private final Long customerId;
    private final Long productId;
    private final int quantity;

    public CartItem(final Long id, final Long customerId, final Long productId, final int quantity) {
        validateQuantity(quantity);
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
    }

    private void validateQuantity(final int quantity) {
        if (quantity <= 0) {
            throw new InvalidInputException("부적절한 수량 값입니다.");
        }
    }

    public static CartItem ofNullProductId(final Long id,
                                           final Long customerId,
                                           final int quantity) {
        return new CartItem(id, customerId, null, quantity);
    }

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}

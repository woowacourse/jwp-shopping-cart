package woowacourse.shoppingcart.domain;

import java.util.Objects;

public class CartItem {
    private final Long cartItemId;
    private Long customerId;
    private Long productId;
    private int quantity;
    private boolean checked;

    public CartItem(Long cartItemId, Long customerId, Long productId, int quantity, boolean checked) {
        this.cartItemId = cartItemId;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
        this.checked = checked;
    }

    public CartItem(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Long getCartItemId() {
        return cartItemId;
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

    public boolean getChecked() {
        return checked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CartItem cartItem = (CartItem) o;
        return quantity == cartItem.quantity && checked == cartItem.checked && Objects.equals(cartItemId,
                cartItem.cartItemId) && Objects.equals(customerId, cartItem.customerId)
                && Objects.equals(productId, cartItem.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartItemId, customerId, productId, quantity, checked);
    }
}

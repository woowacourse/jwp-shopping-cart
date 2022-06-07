package woowacourse.shoppingcart.domain;

import java.util.Objects;

public class CartItem {

    private Long id;
    private Long customerId;
    private Long productId;
    private int quantity;
    private boolean checked;

    public CartItem(Long id, Long customerId, Long productId, int quantity, boolean checked) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
        this.checked = checked;
    }

    public Long getProductId() {
        return productId;
    }

    public boolean getChecked() {
        return checked;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return checked == cartItem.checked && Objects.equals(id, cartItem.id) && Objects.equals(customerId, cartItem.customerId) && Objects.equals(productId, cartItem.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, productId, checked);
    }
}

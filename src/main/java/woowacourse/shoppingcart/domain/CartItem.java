package woowacourse.shoppingcart.domain;

import java.util.Objects;

public class CartItem {
    private Id id;
    private Id customerId;
    private Id productId;
    private Quantity quantity;
    private boolean checked;

    public CartItem(Long id, Long customerId, Long productId, int quantity, boolean checked) {
        this.id = Id.from(id, "상품 항목");
        this.customerId = Id.from(customerId, "사용자");
        this.productId = Id.from(productId, "상품");
        this.quantity = new Quantity(quantity);
        this.checked = checked;
    }

    public Long getId() {
        return id.getId();
    }

    public Long getProductId() {
        return productId.getId();
    }

    public boolean getChecked() {
        return checked;
    }

    public int getQuantity() {
        return quantity.getQuantity();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return checked == cartItem.checked && Objects.equals(id, cartItem.id) && Objects.equals(customerId, cartItem.customerId) && Objects.equals(productId, cartItem.productId) && Objects.equals(quantity, cartItem.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, productId, quantity, checked);
    }
}

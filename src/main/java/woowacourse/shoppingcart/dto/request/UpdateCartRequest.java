package woowacourse.shoppingcart.dto.request;

import woowacourse.shoppingcart.domain.Cart;

public class UpdateCartRequest {

    private Long id;
    private int quantity;
    private boolean checked;

    public UpdateCartRequest() {
    }

    public UpdateCartRequest(Long id, int quantity, boolean checked) {
        this.id = id;
        this.quantity = quantity;
        this.checked = checked;
    }

    public Cart toCart() {
        return new Cart(id, quantity, checked);
    }

    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isChecked() {
        return checked;
    }
}

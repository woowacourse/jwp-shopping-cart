package woowacourse.shoppingcart.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import woowacourse.shoppingcart.domain.Cart;

public class UpdateCartRequest {

    @Positive
    private Long id;

    @Positive
    private Integer quantity;

    @NotNull
    private Boolean checked;

    private UpdateCartRequest() {
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

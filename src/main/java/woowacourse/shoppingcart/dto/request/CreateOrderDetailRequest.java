package woowacourse.shoppingcart.dto.request;

import javax.validation.constraints.NotNull;

public class CreateOrderDetailRequest {

    @NotNull(message = "장바구니 ID를 입력해주세요😉")
    private Long cartId;
    @NotNull(message = "상품 수량을 입력해주세요😉")
    private int quantity;

    private CreateOrderDetailRequest() {
    }

    public CreateOrderDetailRequest(final Long cartId, final int quantity) {
        this.cartId = cartId;
        this.quantity = quantity;
    }

    public Long getCartId() {
        return cartId;
    }

    public int getQuantity() {
        return quantity;
    }
}

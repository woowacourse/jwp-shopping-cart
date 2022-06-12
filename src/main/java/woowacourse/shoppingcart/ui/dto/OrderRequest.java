package woowacourse.shoppingcart.ui.dto;

import javax.validation.constraints.NotNull;

public class OrderRequest {

    @NotNull(message = "장바구니 정보는 빈 값일 수 없습니다.")
    private Long cartId;

    public OrderRequest() {
    }

    public OrderRequest(Long cartId) {
        this.cartId = cartId;
    }

    public Long getCartId() {
        return cartId;
    }
}

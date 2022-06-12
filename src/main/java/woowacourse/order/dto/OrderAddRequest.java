package woowacourse.order.dto;

import javax.validation.constraints.NotNull;

public class OrderAddRequest {

    @NotNull(message = "카트 아이템 id를 입력해 주세요.")
    private Long cartItemId;

    private OrderAddRequest() {
    }

    public OrderAddRequest(final Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Long getCartItemId() {
        return cartItemId;
    }
}

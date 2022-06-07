package woowacourse.shoppingcart.application.dto;

import woowacourse.shoppingcart.dto.request.OrderRequest;

public class OrderDto {

    private final Long cartId;
    private final int quantity;

    private OrderDto(final Long cartId, final int quantity) {
        this.cartId = cartId;
        this.quantity = quantity;
    }

    public static OrderDto from(final OrderRequest request) {
        return new OrderDto(request.getCartId(), request.getQuantity());
    }

    public Long getCartId() {
        return cartId;
    }

    public int getQuantity() {
        return quantity;
    }
}

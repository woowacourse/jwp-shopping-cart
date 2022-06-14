package woowacourse.shoppingcart.application.dto;

import woowacourse.shoppingcart.dto.request.CartRequest;

public class CartDto {

    private Long productId;
    private int quantity;

    private CartDto() {}

    public CartDto(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public static CartDto from(final CartRequest request) {
        return new CartDto(request.getProductId(), request.getQuantity());
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}

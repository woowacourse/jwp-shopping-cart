package woowacourse.shoppingcart.cart.application.dto.request;

import javax.validation.constraints.NotNull;

public class CartPutRequest {

    @NotNull
    private Long quantity;

    public CartPutRequest() {
    }

    public CartPutRequest(Long quantity) {
        this.quantity = quantity;
    }

    public Long getQuantity() {
        return quantity;
    }
}

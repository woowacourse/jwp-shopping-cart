package woowacourse.shoppingcart.dto;

public class OrderRequest {

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

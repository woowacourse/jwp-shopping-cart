package woowacourse.shoppingcart.dto.cartitem;

public class CartItemDeleteRequest {

    private Long id;

    public CartItemDeleteRequest() {
    }

    public CartItemDeleteRequest(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

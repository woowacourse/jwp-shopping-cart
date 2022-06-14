package woowacourse.shoppingcart.dto;

public class CartItemIdRequest {

    private Long id;

    public CartItemIdRequest() {
    }

    public CartItemIdRequest(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

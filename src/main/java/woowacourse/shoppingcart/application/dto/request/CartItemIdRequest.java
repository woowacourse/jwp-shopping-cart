package woowacourse.shoppingcart.application.dto.request;

public class CartItemIdRequest {

    private final Long id;

    public CartItemIdRequest(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

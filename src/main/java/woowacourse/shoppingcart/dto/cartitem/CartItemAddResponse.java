package woowacourse.shoppingcart.dto.cartitem;

public class CartItemAddResponse {

    private final Long id;
    private final Integer quantity;

    public CartItemAddResponse(final Long id, final Integer quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }
}

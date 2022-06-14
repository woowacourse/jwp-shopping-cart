package woowacourse.shoppingcart.dto.cartitem;

public class CartItemCreateResponse {

    private final Long id;
    private final Integer addedQuantity;

    public CartItemCreateResponse(final Long id, final Integer addedQuantity) {
        this.id = id;
        this.addedQuantity = addedQuantity;
    }

    public Long getId() {
        return id;
    }

    public Integer getAddedQuantity() {
        return addedQuantity;
    }
}

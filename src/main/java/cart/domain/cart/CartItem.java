package cart.domain.cart;

import cart.domain.Id;

public class CartItem {

    private final Id productId;

    public CartItem(Id productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId.getId();
    }

}

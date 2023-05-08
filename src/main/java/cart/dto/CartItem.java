package cart.dto;

import cart.entity.CartEntity;

public class CartItem {

    private final int userId;
    private final int productId;

    public CartItem(int userId, int productId) {
        this.userId = userId;
        this.productId = productId;
    }

    public static CartItem from(final CartEntity cartEntity) {
        return new CartItem(cartEntity.getUserId(), cartEntity.getProductId());
    }

    public int getUserId() {
        return userId;
    }

    public int getProductId() {
        return productId;
    }
}

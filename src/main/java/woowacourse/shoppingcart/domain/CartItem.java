package woowacourse.shoppingcart.domain;

public class CartItem {
    private final CartItemInfo cartItemInfo;
    private final int quantity;

    public CartItem(CartItemInfo cartItemInfo, int quantity) {
        this.cartItemInfo = cartItemInfo;
        this.quantity = quantity;
    }

    public static CartItem of(Long id, String name, int price, String thumbnail, int quantity) {
        return new CartItem(CartItemInfo.of(id, name, price, thumbnail), quantity);
    }

    public CartItemInfo getCartItem() {
        return cartItemInfo;
    }

    public Product getProduct() {
        return cartItemInfo.getProduct();
    }

    public int getQuantity() {
        return quantity;
    }
}

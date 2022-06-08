package woowacourse.shoppingcart.dto;

public class CartItemResponse {
    private Long cartItemId;
    private ProductResponse product;
    private int quantity;

    public CartItemResponse() {
    }

    public CartItemResponse(Long cartItemId, ProductResponse product, int quantity) {
        this.cartItemId = cartItemId;
        this.product = product;
        this.quantity = quantity;
    }

    public Long getCartItemId() {
        return cartItemId;
    }

    public ProductResponse getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "CartResponse{" +
                "product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}

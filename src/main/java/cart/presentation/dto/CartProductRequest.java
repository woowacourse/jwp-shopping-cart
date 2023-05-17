package cart.presentation.dto;

public class CartProductRequest {

    private Integer productId;
    private Integer cartId;

    public CartProductRequest(Integer productId, Integer cartId) {
        this.productId = productId;
        this.cartId = cartId;
    }

    public Integer getProductId() {
        return productId;
    }

    public Integer getCartId() {
        return cartId;
    }
    

}

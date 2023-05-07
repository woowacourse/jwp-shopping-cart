package cart.presentation.dto;

public class CartProductResponse {

    private Integer id;
    private Integer productId;
    private Integer cartId;

    public CartProductResponse(Integer id, Integer productId, Integer cartId) {
        this.id = id;
        this.productId = productId;
        this.cartId = cartId;
    }

    public Integer getId() {
        return id;
    }

    public Integer getProductId() {
        return productId;
    }

    public Integer getCartId() {
        return cartId;
    }
}

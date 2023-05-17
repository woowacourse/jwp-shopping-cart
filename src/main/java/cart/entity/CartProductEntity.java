package cart.entity;

public class CartProductEntity {

    private Integer id;
    private Integer productId;
    private Integer cartId;

    public CartProductEntity(Integer id, Integer productId, Integer cartId) {
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

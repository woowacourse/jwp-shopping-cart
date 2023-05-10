package cart.dto;

import cart.entity.Cart;
import cart.entity.Product;

public class CartProductResponse {
    private final int id;

    private final Product product;

    private CartProductResponse(int id, Product product) {
        this.id = id;
        this.product = product;
    }

    public static CartProductResponse from(Cart cart) {
        return new CartProductResponse(cart.getId(), cart.getProduct());
    }

    public int getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }
}

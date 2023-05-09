package cart.dto;

import cart.entity.Product;

public class CartProductResponse {
    private final int id;

    private final Product product;

    private CartProductResponse(int id, Product product) {
        this.id = id;
        this.product = product;
    }

    public static CartProductResponse from(int id, String name, int price, String image) {
        return new CartProductResponse(id, new Product(name, price, image));
    }

    public int getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }
}

package cart.dto;

import cart.domain.cart.entity.Cart;
import cart.domain.product.entity.Product;

public class CartResponse {

    private Long id;
    private String productName;
    private String productImageUrl;
    private int productPrice;

    public CartResponse() {
    }

    public CartResponse(final Long id, final String productName, final String productImageUrl,
        final int productPrice) {
        this.id = id;
        this.productName = productName;
        this.productImageUrl = productImageUrl;
        this.productPrice = productPrice;
    }

    public static CartResponse of(final Cart cart) {
        final Product product = cart.getProduct();
        return new CartResponse(cart.getId(), product.getName(), product.getImageUrl(),
            product.getPrice());
    }

    public Long getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public int getProductPrice() {
        return productPrice;
    }
}

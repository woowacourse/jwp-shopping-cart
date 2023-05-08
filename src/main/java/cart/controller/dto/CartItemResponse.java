package cart.controller.dto;

import cart.domain.cart.Cart;
import cart.domain.product.Product;

import java.util.List;
import java.util.stream.Collectors;

public class CartItemResponse {

    private final Long id;
    private final String productName;
    private final Long productPrice;
    private final String productImageUrl;

    public CartItemResponse(final Long id, final Product product) {
        this.id = id;
        this.productName = product.getName();
        this.productPrice = product.getPrice();
        this.productImageUrl = product.getImageUrl();
    }

    public static List<CartItemResponse> createResponse(final Cart cart) {
        return cart.getCartItems().stream()
                .map(cartItem -> new CartItemResponse(cartItem.getId(), cartItem.getProduct()))
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public Long getProductPrice() {
        return productPrice;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }
}

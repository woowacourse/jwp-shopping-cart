package cart.service.cart.domain;

import cart.service.cart.dto.ProductResponse;

import java.util.List;
import java.util.stream.Collectors;

public class CartItems {

    private final List<CartItem> cartItems;

    public CartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public List<ProductResponse> toProductResponse() {
        return cartItems.stream()
                .map(p -> new ProductResponse(p.getProductId(), p.getProductName(), p.getProductImageUrl(), p.getProductPrice()))
                .collect(Collectors.toList());
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }
}

package woowacourse.shoppingcart.cart.dto;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.cart.domain.Cart;
import woowacourse.shoppingcart.product.domain.Product;

public class CartItemsResponse {

    private final List<CartItemResponse> products;

    private CartItemsResponse(List<CartItemResponse> products) {
        this.products = products;
    }

    public static CartItemsResponse from(List<Cart> carts) {
        final List<CartItemResponse> responses = carts.stream()
                .map(it -> new Product(it.getProductId(), it.getName(), it.getPrice(), it.getImageUrl()))
                .map(it -> new CartItemResponse(it, calculateQuantity(it, carts)))
                .distinct()
                .collect(Collectors.toList());
        return new CartItemsResponse(responses);
    }

    private static Integer calculateQuantity(Product product, List<Cart> carts) {
        return Math.toIntExact(carts.stream()
                .filter(it -> it.getProductId().equals(product.getId()))
                .count());
    }

    public List<CartItemResponse> getProducts() {
        return products;
    }
}

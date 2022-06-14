package woowacourse.shoppingcart.dto;

import java.util.ArrayList;
import java.util.List;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.CartItems;

public class CartItemResponse {
    private Long id;
    private Long productId;
    private String name;
    private String imageUrl;
    private int price;
    private int quantity;

    private CartItemResponse() {
    }

    public CartItemResponse(Long id, Long productId, String name, String imageUrl, int price, int quantity) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.quantity = quantity;
    }

    public static CartItemResponse of(Long id, CartItem cartItem) {
        return new CartItemResponse(id, cartItem.getProductId(), cartItem.getName(), cartItem.getImageUrl(),
                cartItem.getPrice(), cartItem.getQuantity());
    }

    public static List<CartItemResponse> of(CartItems cartItems) {
        List<CartItemResponse> response = new ArrayList<>();
        for (CartItem cartItem : cartItems.getCartItems()) {
            response.add(CartItemResponse.from(cartItem));
        }
        return response;
    }

    private static CartItemResponse from(CartItem cartItem) {
        return new CartItemResponse(cartItem.getId(), cartItem.getProductId(), cartItem.getName(),
                cartItem.getImageUrl(), cartItem.getPrice(), cartItem.getQuantity());
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}

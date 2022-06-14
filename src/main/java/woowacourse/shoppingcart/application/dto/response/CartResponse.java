package woowacourse.shoppingcart.application.dto.response;

import woowacourse.shoppingcart.domain.Cart;

import java.util.List;
import java.util.stream.Collectors;

public class CartResponse {

    private final Long id;
    private final Long productId;
    private final String name;
    private final String imageUrl;
    private final int price;
    private final int quantity;

    private CartResponse(final Long id, final Long productId, final String name, final String imageUrl, final int price, final int quantity) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.quantity = quantity;
    }

    public static CartResponse from(final Cart cart) {
        return new CartResponse(cart.getId(), cart.getProductId(),
                cart.getName(), cart.getImageUrl(), cart.getPrice(), cart.getQuantity());
    }

    public static List<CartResponse> from(final List<Cart> carts) {
        return carts.stream()
                .map(CartResponse::from)
                .collect(Collectors.toUnmodifiableList());
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

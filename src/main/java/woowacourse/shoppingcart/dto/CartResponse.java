package woowacourse.shoppingcart.dto;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.domain.Cart;

public class CartResponse {

    private Long id;
    private Long productId;
    private String name;
    private int price;
    private String imageUrl;
    private int quantity;

    public CartResponse() {
    }

    public CartResponse(Long id, Long productId, String name, int price, String imageUrl, int quantity) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    public static CartResponse of(Cart cart) {
        return new CartResponse(cart.getId(), cart.getProductId(), cart.getName(), cart.getPrice(), cart.getImageUrl(),
                cart.getQuantity());
    }

    public static List<CartResponse> toCartResponses(List<Cart> carts) {
        return carts.stream()
                .map(CartResponse::of)
                .collect(Collectors.toList());
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

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getQuantity() {
        return quantity;
    }
}

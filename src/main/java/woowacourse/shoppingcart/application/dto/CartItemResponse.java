package woowacourse.shoppingcart.application.dto;

import woowacourse.shoppingcart.domain.cart.CartItem;

public class CartItemResponse {

    private final Long id;
    private final Long productId;
    private final String name;
    private final Integer price;
    private final String imageUrl;

    public CartItemResponse() {
        this(null, null, null, null, null);
    }

    private CartItemResponse(Long id, Long productId, String name, Integer price, String imageUrl) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public static CartItemResponse from(CartItem cartItem) {
        return new CartItemResponse(
            cartItem.getId(),
            cartItem.getProductId(),
            cartItem.getName(),
            cartItem.getPrice(),
            cartItem.getImageUrl()
        );
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

    public Integer getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}

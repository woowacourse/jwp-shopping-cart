package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.CartItem;

public class CartItemResponse {

    private final Long id;
    private final String name;
    private final Integer price;
    private final Integer quantity;
    private final String imageUrl;

    public CartItemResponse(Long id, String name, Integer price, Integer quantity, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
    }

    public static CartItemResponse from(CartItem cartItem) {
        return new CartItemResponse(cartItem.getProductId(), cartItem.getName(), cartItem.getPrice(), cartItem.getQuantity(), cartItem.getImageUrl());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}

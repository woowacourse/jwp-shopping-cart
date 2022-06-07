package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Cart;

public class CartResponse {

    private final Long id;
    private final Long productId;
    private final String name;
    private final int price;
    private final String imageUrl;

    public CartResponse(final Long id, final Long productId, final String name,
                        final int price, final String imageUrl) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public static CartResponse from(final Cart cart) {
        return new CartResponse(cart.getId(), cart.getProductId(), cart.getName(), cart.getPrice(), cart.getImageUrl());
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
}

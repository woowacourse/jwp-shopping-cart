package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.cart.Cart;

public class CartResponse {
    private final Long id;
    private final String name;
    private final int price;
    private final String imageUrl;
    private final int quantity;

    private CartResponse(Long id, String name, int price, String imageUrl, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    public static CartResponse from(Cart cart) {
        return new CartResponse(cart.getId(), cart.getName(), cart.getPrice(), cart.getImageUrl(), cart.getQuantity());
    }

    public Long getId() {
        return id;
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

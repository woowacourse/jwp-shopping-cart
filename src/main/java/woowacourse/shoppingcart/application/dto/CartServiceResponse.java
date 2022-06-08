package woowacourse.shoppingcart.application.dto;

import woowacourse.shoppingcart.domain.Cart;

public class CartServiceResponse {

    private final Long id;
    private final Long productId;
    private final String name;
    private final int price;
    private final String imageUrl;
    private final int quantity;

    public CartServiceResponse(final Long id, final Long productId, final String name,
                               final int price, final String imageUrl, final int quantity) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    public static CartServiceResponse from(final Cart cart) {
        return new CartServiceResponse(cart.getId(), cart.getProductId(), cart.getName(), cart.getPrice(), cart.getImageUrl(), cart.getQuantity());
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

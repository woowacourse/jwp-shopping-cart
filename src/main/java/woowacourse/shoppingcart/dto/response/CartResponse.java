package woowacourse.shoppingcart.dto.response;

import woowacourse.shoppingcart.domain.Cart;

public class CartResponse {

    private Long id;
    private int quantity;
    private Long productId;
    private String name;
    private int price;
    private String imageUrl;

    private CartResponse() {
    }

    public CartResponse(final Cart cart) {
        this(cart.getId(), cart.getQuantity(), cart.getProductId(), cart.getName(), cart.getPrice(),
                cart.getImageUrl());
    }

    public CartResponse(final Long id, final int quantity, final Long productId, final String name, final int price,
                        final String imageUrl) {
        this.id = id;
        this.quantity = quantity;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
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

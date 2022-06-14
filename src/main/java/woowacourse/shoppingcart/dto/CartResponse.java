package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Cart;

public class CartResponse {
    private Long id;
    private Long productId;
    private String name;
    private int price;
    private String imageUrl;
    private int quantity;

    private CartResponse() {
    }

    public CartResponse(final Cart cart) {
        this(cart.getId(), cart.getProductId(), cart.getName(), cart.getPrice(), cart.getImageUrl(), cart.getQuantity().getValue());
    }

    public CartResponse(final Long id, final Long productId, final String name, final int price,
                        final String imageUrl, final int quantity) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
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

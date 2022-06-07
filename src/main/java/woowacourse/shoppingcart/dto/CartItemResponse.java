package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.CartItem;

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

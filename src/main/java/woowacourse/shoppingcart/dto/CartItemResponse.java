package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Cart;

public class CartItemResponse {

    private Long id;
    private Long productId;
    private String name;
    private int price;
    private String imageUrl;
    private int totalPrice;
    private int quantity;

    private CartItemResponse() {
    }

    public CartItemResponse(Long id, Long productId, String name, int price, String imageUrl, int totalPrice,
                            int quantity) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.totalPrice = totalPrice;
        this.quantity = quantity;
    }

    public static CartItemResponse from(Cart cart) {
        return new CartItemResponse(cart.getId(), cart.getProductId(), cart.getName(), cart.getPrice(),
                cart.getImageUrl(), cart.getTotalPrice(), cart.getQuantity());
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

    public int getTotalPrice() {
        return totalPrice;
    }

    public int getQuantity() {
        return quantity;
    }
}

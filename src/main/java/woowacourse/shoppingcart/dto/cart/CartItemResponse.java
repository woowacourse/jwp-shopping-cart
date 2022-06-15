package woowacourse.shoppingcart.dto.cart;

import woowacourse.shoppingcart.domain.CartItem;

public class CartItemResponse {
    private Long id;
    private String name;
    private int price;
    private String imageUrl;
    private int quantity;

    public CartItemResponse() {
    }

    public CartItemResponse(Long id, String name, int price, String imageUrl, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    public static CartItemResponse from(CartItem item) {
        return new CartItemResponse(item.getProductId(), item.getName(), item.getPrice(), item.getImageUrl(), item.getQuantity());
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

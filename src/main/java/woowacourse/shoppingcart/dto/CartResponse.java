package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Cart;

public class CartResponse {

    private Long id;
    private String name;
    private Integer price;
    private String imageUrl;
    private Integer quantity;

    private CartResponse() {

    }

    private CartResponse(Long id, String name, Integer price, String imageUrl, Integer quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    public static CartResponse from(Cart cart) {
        return new CartResponse(cart.getProductId(), cart.getName(), cart.getPrice(), cart.getImageUrl(),
                cart.getQuantity());
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

    public String getImageUrl() {
        return imageUrl;
    }

    public Integer getQuantity() {
        return quantity;
    }
}

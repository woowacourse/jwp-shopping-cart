package cart.controller.dto.response;

import cart.domain.CartData;

public class CartResponse {

    private final Long id;
    private final String name;
    private final String imageUrl;
    private final Integer price;

    private CartResponse(Long id, String name, String imageUrl, Integer price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public static CartResponse from(CartData cartData) {
        return new CartResponse(
                cartData.getId(),
                cartData.getName(),
                cartData.getImageUrl(),
                cartData.getPrice()
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Integer getPrice() {
        return price;
    }
}

package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.CartItem;

public class CartDto {

    private Long id;
    private Long productId;
    private String name;
    private int price;
    private String imageUrl;
    private int quantity;

    public CartDto() {
    }

    public CartDto(final Long id, final Long productId, final String name, final int price, final String imageUrl,
                   final int quantity) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    public static CartDto from(CartItem cartItem) {
        return new CartDto(
                cartItem.getId(),
                cartItem.getProductId(),
                cartItem.getName(),
                cartItem.getPrice(),
                cartItem.getImageUrl(),
                cartItem.getQuantity()
        );
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

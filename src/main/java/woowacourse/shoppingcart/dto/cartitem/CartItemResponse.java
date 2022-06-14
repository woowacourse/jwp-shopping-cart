package woowacourse.shoppingcart.dto.cartitem;

import woowacourse.shoppingcart.domain.CartItem;

public class CartItemResponse {

    private final Long id;
    private final Long productId;
    private final String name;
    private final String imageUrl;
    private final Integer price;
    private final Integer quantity;

    public CartItemResponse(final Long id, final Long productId, final String name, final String imageUrl,
                            final Integer price, final Integer quantity) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.quantity = quantity;
    }

    public static CartItemResponse from(final CartItem cartItem) {
        return new CartItemResponse(cartItem.getId(), cartItem.getProductId(), cartItem.getName(),
                cartItem.getImageUrl(), cartItem.getPrice(), cartItem.getQuantity());
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

    public Integer getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }
}

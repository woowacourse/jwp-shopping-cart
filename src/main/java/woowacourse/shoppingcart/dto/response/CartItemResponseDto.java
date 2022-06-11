package woowacourse.shoppingcart.dto.response;

import woowacourse.shoppingcart.domain.CartItem;

public class CartItemResponseDto {
    private final Long productId;
    private final String thumbnailUrl;
    private final String name;
    private final Integer price;
    private final Integer quantity;
    private final Integer count;

    public CartItemResponseDto(final Long productId,
                               final String thumbnailUrl,
                               final String name,
                               final Integer price,
                               final Integer quantity,
                               final Integer count) {
        this.productId = productId;
        this.thumbnailUrl = thumbnailUrl;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.count = count;
    }

    public CartItemResponseDto(CartItem cartItem) {
        this.productId = cartItem.getProductId();
        this.thumbnailUrl = cartItem.getImageUrl();
        this.name = cartItem.getName();
        this.price = cartItem.getPrice();
        this.quantity = cartItem.getQuantity();
        this.count = cartItem.getCount();
    }

    public Long getProductId() {
        return productId;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getCount() {
        return count;
    }
}

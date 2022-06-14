package woowacourse.shoppingcart.dto.cart;

import woowacourse.shoppingcart.domain.cart.CartItem;

public class CartItemResponse {

    private Long productId;
    private String thumbnailUrl;
    private String name;
    private Integer price;
    private Integer quantity;
    private Integer count;

    public CartItemResponse(Long productId, String thumbnailUrl, String name, Integer price, Integer quantity,
        Integer count) {
        this.productId = productId;
        this.thumbnailUrl = thumbnailUrl;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.count = count;
    }

    private CartItemResponse() {
    }

    public CartItemResponse(CartItem cart) {
        this(cart.getProductId(), cart.getImageUrl(), cart.getName(), cart.getPrice(), cart.getQuantity(),
            cart.getCount());
    }

    public static CartItemResponse from(CartItem cartItem) {
        return new CartItemResponse(cartItem);
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

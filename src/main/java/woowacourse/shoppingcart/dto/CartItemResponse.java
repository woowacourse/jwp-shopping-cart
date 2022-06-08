package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.CartItem;

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
        this.productId = cart.getProductId();
        this.thumbnailUrl = cart.getImageUrl();
        this.name = cart.getName();
        this.price = cart.getPrice();
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

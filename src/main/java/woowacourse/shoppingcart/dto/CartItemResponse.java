package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.ThumbnailImage;

public class CartItemResponse {
    private Long id;
    private Long productId;
    private String name;
    private int price;
    private int quantity;
    private ThumbnailImage thumbnailImage;

    public CartItemResponse() {
    }

    public CartItemResponse(Cart cart) {
        this.id = cart.getId();
        this.productId = cart.getProduct().getId();
        this.name = cart.getProduct().getName();
        this.price = cart.getProduct().getPrice();
        this.quantity = cart.getProduct().getStockQuantity();
        this.thumbnailImage = cart.getProduct().getThumbnailImage();
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

    public int getQuantity() {
        return quantity;
    }

    public ThumbnailImage getThumbnailImage() {
        return thumbnailImage;
    }
}

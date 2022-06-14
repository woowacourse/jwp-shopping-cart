package woowacourse.shoppingcart.dto.cartItem;

import woowacourse.shoppingcart.domain.CartItem;
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

    public CartItemResponse(CartItem cartItem) {
        this.id = cartItem.getId();
        this.productId = cartItem.getProduct().getId();
        this.name = cartItem.getProduct().getName();
        this.price = cartItem.getProduct().getPrice();
        this.quantity = cartItem.getQuantity();
        this.thumbnailImage = cartItem.getProduct().getThumbnailImage();
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

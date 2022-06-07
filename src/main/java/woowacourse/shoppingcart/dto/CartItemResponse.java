package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.cart.CartItem;
import woowacourse.shoppingcart.domain.product.Product;

public class CartItemResponse {

    private final long id;
    private final long productId;
    private final int price;
    private final String name;
    private final int quantity;
    private final ThumbnailImageDto thumbnailImage;

    private CartItemResponse(long id, long productId, int price, String name, int quantity,
        ThumbnailImageDto thumbnailImage) {
        this.id = id;
        this.productId = productId;
        this.price = price;
        this.name = name;
        this.quantity = quantity;
        this.thumbnailImage = thumbnailImage;
    }

    public static CartItemResponse from(CartItem cartItem) {
        Product product = cartItem.getProduct();
        return new CartItemResponse(cartItem.getId(), product.getId(), product.getPrice(), product.getName(),
            cartItem.getQuantity(),
            new ThumbnailImageDto(product.getThumbnailImageUrl(), product.getThumbnailImageAlt()));
    }

    public long getId() {
        return id;
    }

    public long getProductId() {
        return productId;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public ThumbnailImageDto getThumbnailImage() {
        return thumbnailImage;
    }
}

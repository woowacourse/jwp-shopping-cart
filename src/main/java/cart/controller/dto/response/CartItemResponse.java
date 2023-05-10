package cart.controller.dto.response;

public final class CartItemResponse {
    private final Long cartId;
    private final Long productId;
    private final String name;
    private final String imageUrl;
    private final int price;
    private final int count;

    public CartItemResponse(Long cartId, Long productId, String name, String imageUrl, int price, int count) {
        this.cartId = cartId;
        this.productId = productId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.count = count;
    }

    public Long getCartId() {
        return cartId;
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

    public int getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }
}

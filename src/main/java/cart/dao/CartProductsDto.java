package cart.dao;

public class CartProductsDto {

    private final Long cartProductId;
    private final Long productId;
    private final String name;
    private final String imageUrl;
    private final Integer price;
    private final String category;

    public CartProductsDto(final Long cartProductId, final Long productId, final String name, final String imageUrl, final Integer price, final String category) {
        this.cartProductId = cartProductId;
        this.productId = productId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.category = category;
    }

    public Long getCartProductId() {
        return cartProductId;
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

    public String getCategory() {
        return category;
    }
}

package cart.dao.cart;

import cart.dao.product.ProductEntity;

public class CartProductDto {

    private final Long cartId;
    private final ProductEntity productEntity;

    public CartProductDto(final Long cartId, final String name, final Integer price, final String imageUrl) {
        this.cartId = cartId;
        this.productEntity = new ProductEntity(name, price, imageUrl);
    }

    public Long getCartId() {
        return cartId;
    }

    public String getName() {
        return productEntity.getName();
    }

    public Integer getPrice() {
        return productEntity.getPrice();
    }

    public String getImageUrl() {
        return productEntity.getImageUrl();
    }
}

package cart.domain;

import cart.dao.entity.CartProductEntity;

import java.util.Objects;

public class CartProduct {

    private final long cartId;
    private final Product product;

    public CartProduct(final long cartId, final Product product) {
        this.cartId = cartId;
        this.product = product;
    }

    public CartProduct(final CartProductEntity cartProductEntity) {
        this.cartId = cartProductEntity.getCartId();
        this.product = new Product(
                cartProductEntity.getName(),
                cartProductEntity.getPrice(),
                cartProductEntity.getImage()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartProduct that = (CartProduct) o;
        return cartId == that.cartId && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartId, product);
    }

    public long getCartId() {
        return cartId;
    }

    public Product getProduct() {
        return product;
    }
}

package cart.dao.cart;

import cart.dao.product.ProductEntity;
import cart.dao.user.CartUserEntity;
import java.util.List;

public class CartEntity {
    private final CartUserEntity cartUserEntity;
    private final List<ProductEntity> productEntity;

    public CartEntity(CartUserEntity cartUserEntity, List<ProductEntity> productEntity) {
        this.cartUserEntity = cartUserEntity;
        this.productEntity = productEntity;
    }
}

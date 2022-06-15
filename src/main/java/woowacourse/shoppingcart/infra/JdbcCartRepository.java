package woowacourse.shoppingcart.infra;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Carts;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.infra.dao.CartDao;
import woowacourse.shoppingcart.infra.dao.entity.CartEntity;
import woowacourse.shoppingcart.infra.dao.entity.ProductEntity;

@Repository
public class JdbcCartRepository implements CartRepository {
    private final CartDao cartDao;

    public JdbcCartRepository(final CartDao cartDao) {
        this.cartDao = cartDao;
    }

    @Override
    public Carts findCartsByCustomerId(final long memberId) {
        final List<CartEntity> cartEntities = cartDao.findCartsByMemberId(memberId);

        return new Carts(memberId, toCarts(cartEntities));
    }

    private List<Cart> toCarts(final List<CartEntity> cartEntities) {
        return cartEntities.stream()
                .map(this::toCart)
                .collect(Collectors.toList());
    }

    private Cart toCart(final CartEntity entity) {
        return new Cart(entity.getId(), entity.getCustomerId(), toProduct(entity.getProductEntity()),
                entity.getQuantity());
    }

    private Product toProduct(final ProductEntity entity) {
        return new Product(entity.getId(), entity.getName(), entity.getPrice(), entity.getImageUrl());
    }

    @Override
    public void saveCarts(final Carts carts) {
        final List<CartEntity> cartEntities = toEntities(carts);

        cartDao.save(carts.getCustomerId(), cartEntities);
    }

    @Override
    public void deleteCartItemsByProductIds(final List<Long> productIds) {
        cartDao.deleteByCartIds(productIds);
    }

    private List<CartEntity> toEntities(final Carts carts) {
        return carts.getCarts()
                .stream()
                .map(this::toCartEntity)
                .collect(Collectors.toList());
    }

    private CartEntity toCartEntity(final Cart cart) {
        return new CartEntity(cart.getId(), cart.getCustomerId(), toProductEntity(cart.getProduct()),
                cart.getQuantity());
    }

    private ProductEntity toProductEntity(Product product) {
        return new ProductEntity(product.getId(), product.getName(), product.getPrice(), product.getImageUrl());
    }
}

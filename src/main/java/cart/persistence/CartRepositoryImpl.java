package cart.persistence;

import cart.domain.Cart;
import cart.domain.CartItem;
import cart.domain.CartRepository;
import cart.domain.Product;
import cart.persistence.dao.CartDao;
import cart.persistence.dao.CartItemDao;
import cart.persistence.dao.CartItemWithProductDao;
import cart.persistence.entity.CartItemEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CartRepositoryImpl implements CartRepository {

    private final CartDao cartDao;
    private final CartItemDao cartItemDao;
    private final CartItemWithProductDao cartItemWithProductDao;

    public CartRepositoryImpl(CartDao cartDao, CartItemDao cartItemDao, CartItemWithProductDao cartItemWithProductDao) {
        this.cartDao = cartDao;
        this.cartItemDao = cartItemDao;
        this.cartItemWithProductDao = cartItemWithProductDao;
    }

    @Override
    public Cart save(Cart cart) {
        List<CartItemEntity> entities = mapToCartItemEntities(cart);
        updateCart(cart.getCartId(), entities);
        return cart;
    }

    private List<CartItemEntity> mapToCartItemEntities(Cart cart) {
        Long cartId = cart.getCartId();

        return cart.getCartItems().stream()
                .map(item -> new CartItemEntity(cartId, item.getProductId()))
                .collect(Collectors.toList());
    }

    private void updateCart(Long cartId, List<CartItemEntity> entities) {
        boolean cartExists = cartDao.existsByCartId(cartId);

        if (cartExists) {
            cartItemDao.deleteByCartId(cartId);
            cartItemDao.insertCartItems(entities);
            return;
        }
        cartItemDao.insertCartItems(entities);
    }

    @Override
    public Cart getCartByMemberId(Long memberId) {
        Long cartId = cartDao.findCartIdByMemberId(memberId)
                .orElseGet(() -> cartDao.createCart(memberId));

        List<Product> products = cartItemWithProductDao.findProductsByCartId(cartId);
        List<CartItem> cartItems = mapToCartItems(products);

        return new Cart(cartId, memberId, cartItems);
    }

    private List<CartItem> mapToCartItems(List<Product> products) {
        return products.stream()
                .map(CartItem::new)
                .collect(Collectors.toList());
    }
}

package woowacourse.shoppingcart.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Quantity;
import woowacourse.shoppingcart.domain.cart.CartItem;
import woowacourse.shoppingcart.domain.cart.CartItemRepository;
import woowacourse.shoppingcart.domain.cart.CartItems;
import woowacourse.shoppingcart.entity.CartItemEntity;

@Repository
public class CartItemRepositoryUsingDao implements CartItemRepository {

    private final ProductDao productDao;
    private final CartItemDao cartItemDao;

    public CartItemRepositoryUsingDao(ProductDao productDao, CartItemDao cartItemDao) {
        this.productDao = productDao;
        this.cartItemDao = cartItemDao;
    }

    @Override
    public CartItems findCartItemsByCustomer(long customerId) {
        List<CartItemEntity> cartItemsByCustomerId = cartItemDao.findCartItemsByCustomerId(customerId);
        List<CartItem> cartItems = cartItemsByCustomerId.stream()
            .map(cartItemEntity -> new CartItem(cartItemEntity.getId(),
                productDao.findProductById(cartItemEntity.getProductId()),
                new Quantity(cartItemEntity.getQuantity())))
            .collect(Collectors.toList());
        return new CartItems(customerId, cartItems);
    }

    @Override
    public long addCartItem(long customerId, CartItem cartItem) {
        return cartItemDao.addCartItem(customerId, cartItem);
    }

    @Override
    public CartItem findById(long id) {
        CartItemEntity cartItemEntity = cartItemDao.findCartItemById(id);
        return new CartItem(cartItemEntity.getId(), productDao.findProductById(cartItemEntity.getProductId()),
            new Quantity(cartItemEntity.getQuantity()));
    }

    @Override
    public void update(CartItem updateCartItem) {
        cartItemDao.update(updateCartItem);
    }

    @Override
    public void delete(CartItem deleteCartItem) {
        cartItemDao.delete(deleteCartItem.getId());
    }

}

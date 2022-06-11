package woowacourse.shoppingcart.repository;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.entity.CartItemEntity;

@Component
public class CartItemRepository {
    private final CartItemDao cartItemDao;
    private final ProductRepository productRepository;

    public CartItemRepository(CartItemDao cartItemDao, ProductRepository productRepository) {
        this.cartItemDao = cartItemDao;
        this.productRepository = productRepository;
    }

    public long save(long customerId, CartItem cartItem) {
        return cartItemDao.save(customerId, cartItem);
    }

    public CartItem findById(long id) {
        CartItemEntity cartItemEntity = cartItemDao.findById(id);
        return generateCartItem(cartItemEntity);
    }

    public List<CartItem> findAllByCustomerId(long customerId) {
        List<CartItemEntity> cartItemEntities = cartItemDao.findAllByCustomerId(customerId);
        return cartItemEntities.stream()
                .map(this::generateCartItem)
                .collect(Collectors.toList());
    }

    public boolean isProductExisting(long customerId, long productId) {
        return cartItemDao.isProductExisting(customerId, productId);
    }

    public void update(long cartItemId, CartItem newCartItem) {
        cartItemDao.update(cartItemId, newCartItem);
    }

    public void delete(long cartItemId) {
        cartItemDao.delete(cartItemId);
    }

    private CartItem generateCartItem(CartItemEntity cartItemEntity) {
        Product product = productRepository.findById(cartItemEntity.getProductId());
        return new CartItem(cartItemEntity.getId(), product, cartItemEntity.getQuantity());
    }
}

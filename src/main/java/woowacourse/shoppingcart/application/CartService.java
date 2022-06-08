package woowacourse.shoppingcart.application;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.dao.entity.CartItemEntity;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.customer.Email;
import woowacourse.shoppingcart.dto.CartAdditionRequest;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

@Service
@Transactional(readOnly = true)
public class CartService {

    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public CartService(final CartItemDao cartItemDao, final CustomerDao customerDao, final ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    public List<Cart> findCartsByEmail(final String email) {
        final List<CartItemEntity> cartItemEntities = findCartItemEntitiesByEmail(email);

        final List<Cart> carts = new ArrayList<>();
        for (final CartItemEntity entity : cartItemEntities) {
            final Product product = productDao.findProductById(entity.getProductId());
            carts.add(new Cart(product, entity.getQuantity()));
        }
        return carts;
    }

    @Transactional
    public void addCartItem(final CartAdditionRequest cartAdditionRequest, final String email) {
        final Long customerId = customerDao.findIdByEmail(new Email(email));
        try {
            cartItemDao.addCartItem(customerId, cartAdditionRequest.getProductId(), cartAdditionRequest.getQuantity());
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    @Transactional
    public void deleteCartItem(final String email, final Long productId) {
        List<CartItemEntity> cartItemEntities = findCartItemEntitiesByEmail(email);
        cartItemDao.deleteCartItem(findCartId(cartItemEntities, productId));
    }

    private List<CartItemEntity> findCartItemEntitiesByEmail(final String email) {
        final Long customerId = customerDao.findIdByEmail(new Email(email));
        return cartItemDao.findAllByCustomerId(customerId);
    }

    private Long findCartId(List<CartItemEntity> cartItemEntities, Long productId) {
        CartItemEntity cartItemEntity = cartItemEntities.stream()
                .filter(it -> it.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(NotInCustomerCartItemException::new);
        return cartItemEntity.getId();
    }
}

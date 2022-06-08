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
import woowacourse.shoppingcart.dto.CartUpdateRequest;
import woowacourse.shoppingcart.exception.InvalidCartItemException;
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
    public void addCartItem(final String email, final CartAdditionRequest cartAdditionRequest) {
        final Long customerId = customerDao.findIdByEmail(new Email(email));
        final Product product = productDao.findProductById(cartAdditionRequest.getProductId());

        if (cartItemDao.existCartItem(customerId, product.getId())) {
            addQuantity(cartAdditionRequest, customerId, product);
            return;
        }
        validateStock(product.getStock(), cartAdditionRequest.getQuantity());
        try {
            cartItemDao.addCartItem(customerId, cartAdditionRequest.getProductId(), cartAdditionRequest.getQuantity());
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    private void addQuantity(CartAdditionRequest cartAdditionRequest, Long customerId, Product product) {
        CartItemEntity cartItemEntity = cartItemDao.findByCustomerIdAndProductId(customerId, product.getId());
        int newQuantity = cartItemEntity.getQuantity() + cartAdditionRequest.getQuantity();
        validateStock(product.getStock(), newQuantity);
        cartItemDao.updateCartItem(customerId, product.getId(), newQuantity);
    }

    @Transactional
    public void updateCartItem(final String email, final CartUpdateRequest cartUpdateRequest) {
        final Long customerId = customerDao.findIdByEmail(new Email(email));
        final Product product = productDao.findProductById(cartUpdateRequest.getProductId());

        validateExistProduct(customerId, product);
        validateStock(product.getStock(), cartUpdateRequest.getQuantity());

        cartItemDao.updateCartItem(customerId, cartUpdateRequest.getProductId(), cartUpdateRequest.getQuantity());
    }

    private void validateExistProduct(Long customerId, Product product) {
        if (!cartItemDao.existCartItem(customerId, product.getId())) {
            throw new InvalidCartItemException("장바구니에 해당 상품이 존재하지 않습니다.");
        }
    }

    private void validateStock(int stock, int quantity) {
        if (stock < quantity) {
            throw new InvalidCartItemException("재고가 부족합니다. 현재 재고: " + stock);
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

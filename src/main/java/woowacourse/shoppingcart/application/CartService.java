package woowacourse.shoppingcart.application;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.entity.CartItemEntity;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.customer.Email;
import woowacourse.shoppingcart.dto.cart.CartAdditionRequest;
import woowacourse.shoppingcart.dto.cart.CartUpdateRequest;
import woowacourse.shoppingcart.exception.InvalidCartItemException;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

@Service
@Transactional(readOnly = true)
public class CartService {

    private final CartItemDao cartItemDao;
    private final CustomerService customerService;
    private final ProductService productService;

    public CartService(final CartItemDao cartItemDao, final CustomerService customerService,
                       final ProductService productService) {
        this.cartItemDao = cartItemDao;
        this.customerService = customerService;
        this.productService = productService;
    }

    public List<Cart> findCartsByEmail(final Email email) {
        final List<CartItemEntity> cartItemEntities = findCartItemEntitiesByEmail(email);

        final List<Cart> carts = new ArrayList<>();
        for (final CartItemEntity entity : cartItemEntities) {
            final Product product = productService.findById(entity.getProductId());
            carts.add(new Cart(product, entity.getQuantity()));
        }
        return carts;
    }

    @Transactional
    public void addCartItem(final Email email, final CartAdditionRequest cartAdditionRequest) {
        final Long customerId = customerService.findIdByEmail(email);
        final Product product = productService.findById(cartAdditionRequest.getProductId());

        if (cartItemDao.existCartItem(customerId, product.getId())) {
            addQuantity(cartAdditionRequest, customerId, product);
            return;
        }
        validateStock(product.getStock(), cartAdditionRequest.getQuantity());
        try {
            cartItemDao.save(customerId, cartAdditionRequest.getProductId(), cartAdditionRequest.getQuantity());
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    private void addQuantity(CartAdditionRequest cartAdditionRequest, Long customerId, Product product) {
        CartItemEntity cartItemEntity = cartItemDao.findByCustomerIdAndProductId(customerId, product.getId());
        int newQuantity = cartItemEntity.getQuantity() + cartAdditionRequest.getQuantity();
        validateStock(product.getStock(), newQuantity);
        cartItemDao.updateQuantity(customerId, product.getId(), newQuantity);
    }

    @Transactional
    public void updateCartItem(final Email email, final CartUpdateRequest cartUpdateRequest) {
        final Long customerId = customerService.findIdByEmail(email);
        final Product product = productService.findById(cartUpdateRequest.getProductId());

        validateExistProduct(customerId, product);
        validateStock(product.getStock(), cartUpdateRequest.getQuantity());

        cartItemDao.updateQuantity(customerId, cartUpdateRequest.getProductId(), cartUpdateRequest.getQuantity());
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
    public void deleteCartItem(final Email email, final Long productId) {
        List<CartItemEntity> cartItemEntities = findCartItemEntitiesByEmail(email);
        cartItemDao.delete(findCartId(cartItemEntities, productId));
    }

    private List<CartItemEntity> findCartItemEntitiesByEmail(final Email email) {
        final Long customerId = customerService.findIdByEmail(email);
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

package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.entity.CartItemEntity;
import woowacourse.shoppingcart.exception.InvalidCartItemException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;
    private final ProductService productService;

    public CartService(final CartItemDao cartItemDao, final ProductService productService) {
        this.cartItemDao = cartItemDao;
        this.productService = productService;
    }

    public Long addCart(final int customerId, final Long productId, final int quantity) {
        return cartItemDao.addCartItem(customerId, productId, quantity);
    }

    @Transactional(readOnly = true)
    public List<CartItemResponse> findCartsByCustomerId(final int customerId) {
        final List<CartItemEntity> cartItemEntities = cartItemDao.findCartByCustomerId(customerId);

        return cartItemEntities.stream()
                .map(this::convertEntityCartItemResponse)
                .collect(Collectors.toList());
    }

    private CartItemResponse convertEntityCartItemResponse(CartItemEntity cartItemEntity) {
        return new CartItemResponse(
                cartItemEntity.getId(),
                productService.findProductById(cartItemEntity.getProductId()),
                cartItemEntity.getQuantity()
        );
    }

    public void deleteCart(final Long cartId, final int customerId) {
        validateCustomerCart(cartId, customerId);
        cartItemDao.deleteCartItem(cartId);
    }

    private void validateCustomerCart(final Long cartId, final int customerId) {
        if (!cartItemDao.hasCartItem(cartId, customerId)) {
            throw new NotInCustomerCartItemException();
        }
    }

    @Transactional(readOnly = true)
    public boolean hasProduct(int customerId, Long productId) {
        return cartItemDao.hasProduct(customerId, productId);
    }

    public void updateCartItem(Long cartItem, int customerId, Long productId, int quantity) {
        if (!cartItemDao.hasCart(cartItem, customerId, productId)) {
            throw new InvalidCartItemException();
        }
        cartItemDao.updateCartItem(cartItem, customerId, productId, quantity);
    }
}

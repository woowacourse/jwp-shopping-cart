package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.CartItemResponses;
import woowacourse.shoppingcart.entity.CartItemEntity;
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

    public CartItemResponses findCartsByCustomerId(final int customerId) {
        final List<CartItemEntity> cartItemEntities = cartItemDao.findCartByCustomerId(customerId);

        return new CartItemResponses(cartItemEntities.stream()
                .map(this::convertCartItemResponse)
                .collect(Collectors.toList()));
    }

    private CartItemResponse convertCartItemResponse(CartItemEntity cartItemEntity) {
        return new CartItemResponse(
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
}

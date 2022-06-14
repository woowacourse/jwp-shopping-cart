package woowacourse.shoppingcart.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.exception.dto.ErrorResponse;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.CartItemIds;
import woowacourse.shoppingcart.entity.CartItemEntity;
import woowacourse.shoppingcart.exception.AlreadyExistException;
import woowacourse.shoppingcart.exception.NotExistException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartItemService {

    private final CartItemDao cartItemDao;
    private final ProductService productService;

    public CartItemService(final CartItemDao cartItemDao, final ProductService productService) {
        this.cartItemDao = cartItemDao;
        this.productService = productService;
    }

    public Long addCart(final Customer customer, final Long productId) {
        validateAlreadyExistCartItem(customer, productId);
        return cartItemDao.addCartItem(customer, productId);
    }

    public void updateQuantity(Customer customer, Long cartItemId, int quantity) {
        validateCustomerCartItem(customer, cartItemId);
        cartItemDao.updateQuantity(customer.getId(), cartItemId, quantity);
    }

    public CartItem findById(Customer customer, Long cartItemId) {
        CartItemEntity cartItemEntity = cartItemDao.findById(customer.getId(), cartItemId);
        return toCartItem(cartItemEntity);
    }

    public List<CartItem> findCartItems(final Customer customer) {
        List<CartItemEntity> cartItemEntities = cartItemDao.findCartItemsByCustomerId(customer.getId());
        return cartItemEntities.stream()
                .map(this::toCartItem)
                .collect(Collectors.toUnmodifiableList());
    }

    public void deleteCartItems(final Customer customer, final CartItemIds cartItemIds) {
        for (Long cartItemId : cartItemIds.getCartItemIds()) {
            validateCustomerCartItem(customer, cartItemId);
            cartItemDao.deleteCartItem(cartItemId);
        }
    }

    public void deleteById(Long cartItemId) {
        cartItemDao.deleteCartItem(cartItemId);
    }

    private void validateAlreadyExistCartItem(Customer customer, Long productId) {
        boolean result = cartItemDao.hasCustomerProductItem(customer.getId(), productId);
        if (result) {
            throw new AlreadyExistException("이미 장바구니에 담긴 상품입니다.", ErrorResponse.ALREADY_EXIST_CART_ITEM);
        }
    }

    private void validateCustomerCartItem(Customer customer, Long cartItemId) {
        boolean result = cartItemDao.hasCustomerCartItem(customer.getId(), cartItemId);
        if (!result) {
            throw new NotExistException("장바구니에 없는 아이템입니다.", ErrorResponse.NOT_EXIST_CART_ITEM);
        }
    }

    private CartItem toCartItem(CartItemEntity entity) {
        Product product = productService.findById(entity.getProductId());
        return new CartItem(entity.getId(), product, entity.getQuantity());
    }
}

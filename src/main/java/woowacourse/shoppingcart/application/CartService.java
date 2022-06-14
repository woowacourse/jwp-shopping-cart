package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.common.exception.CartItemException;
import woowacourse.common.exception.NotFoundException;
import woowacourse.common.exception.dto.ErrorResponse;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.dto.cartItem.CartItemResponse;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public CartService(CartItemDao cartItemDao, CustomerDao customerDao, ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    public CartItemResponse addCart(final Long productId, final int quantity, final String email) {
        final Long customerId = customerDao.getIdByEmail(email);
        validateProductExist(productId);
        validateProductAlreadyExistInCart(productId, customerId);

        Long cartItemId = cartItemDao.save(customerId, quantity, productId);
        CartItem cartItem = cartItemDao.getById(cartItemId);
        return new CartItemResponse(cartItem);
    }

    public List<CartItemResponse> findCartsByEmail(final String email) {
        final List<CartItem> cartItems = findCartIdsByEmail(email);

        return cartItems.stream()
                .map(CartItemResponse::new)
                .collect(Collectors.toUnmodifiableList());
    }

    private List<CartItem> findCartIdsByEmail(final String email) {
        final Long customerId = customerDao.getIdByEmail(email);
        return cartItemDao.getAllByCustomerId(customerId);
    }

    public CartItemResponse findCart(Long cartId) {
        CartItem cartItem = cartItemDao.getById(cartId);
        return new CartItemResponse(cartItem);
    }

    private void validateProductExist(Long productId) {
        if (!productDao.existsById(productId)) {
            throw new NotFoundException("존재하지 않는 상품입니다.", ErrorResponse.NOT_EXIST_PRODUCT);
        }
    }

    private void validateProductAlreadyExistInCart(Long productId, Long customerId) {
        if (cartItemDao.existsProductIdAndCustomerId(productId, customerId)) {
            throw new CartItemException("이미 장바구니에 존재하는 제품입니다.", ErrorResponse.ALREADY_EXIST);
        }
    }

    public void deleteCarts(final String email, final List<Long> cartIds) {
        for (Long cartId : cartIds) {
            validateCustomerCart(cartId, email);
            cartItemDao.delete(cartId);
        }
    }

    private void validateCustomerCart(final Long cartId, final String email) {
        List<Long> cartIds = findCartIdsByEmail(email).stream()
                .map(CartItem::getId)
                .collect(Collectors.toUnmodifiableList());
        if (cartIds.contains(cartId)) {
            return;
        }
        throw new NotFoundException("존재하지 않는 장바구니입니다.", ErrorResponse.NOT_EXIST_CART_ITEM);
    }

    public void updateCart(final String email, final Long cartItemId, final int quantity) {
        validateCustomerCart(cartItemId, email);
        CartItem oldCartItem = cartItemDao.getById(cartItemId);
        CartItem newCartItem = new CartItem(oldCartItem.getId(), quantity, oldCartItem.getProduct());
        cartItemDao.updateCartQuantity(newCartItem);
    }
}

package woowacourse.shoppingcart.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.customer.UserName;
import woowacourse.shoppingcart.dto.request.CreateCartItemRequest;
import woowacourse.shoppingcart.dto.request.EditCartItemQuantityRequest;
import woowacourse.shoppingcart.dto.response.CartItemResponse;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;
import woowacourse.shoppingcart.exception.notfound.NotFoundCartItemException;
import woowacourse.shoppingcart.exception.notfound.NotFoundProductException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartItemService {

    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public CartItemService(final CartItemDao cartItemDao, final CustomerDao customerDao, final ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    @Transactional(readOnly = true)
    public List<CartItemResponse> findCartsByCustomerName(final UserName customerName) {
        final Long customerId = customerDao.getIdByUserName(customerName);
        final List<CartItem> cartItems = cartItemDao.findAllByCustomerId(customerId);

        return cartItems.stream()
                .map(item -> new CartItemResponse(item, productDao.getProductById(item.getProductId())))
                .collect(Collectors.toList());
    }

    public Long addCart(final UserName customerName, final CreateCartItemRequest request) {
        final Long customerId = customerDao.getIdByUserName(customerName);
        try {
            final CartItem cartItem = cartItemDao.getByCustomerIdAndProductId(customerId, request.getId());
            return updateExistingCart(cartItem);
        } catch (NotFoundCartItemException e) {
            return addNewCart(request, customerId);
        }
    }

    private Long updateExistingCart(final CartItem cartItem) {
        cartItem.plusQuantity();
        cartItemDao.updateQuantity(cartItem.getId(), cartItem.getQuantity());
        return cartItem.getId();
    }

    private Long addNewCart(final CreateCartItemRequest request, final Long customerId) {
        try {
            return cartItemDao.addCartItem(customerId, request.getId());
        } catch (DataIntegrityViolationException e) {
            throw new NotFoundProductException();
        }
    }

    public void deleteCart(final UserName customerName, final Long cartId) {
        validateCustomerCart(cartId, customerName);
        cartItemDao.deleteCartItem(cartId);
    }

    public void editQuantity(final UserName customerName, final Long cartId,
                             final EditCartItemQuantityRequest request) {
        validateCustomerCart(cartId, customerName);
        cartItemDao.updateQuantity(cartId, request.getQuantity());
    }

    private void validateCustomerCart(final Long cartId, final UserName customerName) {
        final List<Long> cartIds = findCartIdsByCustomerName(customerName);
        if (cartIds.contains(cartId)) {
            return;
        }
        throw new NotInCustomerCartItemException();
    }

    private List<Long> findCartIdsByCustomerName(final UserName customerName) {
        final Long customerId = customerDao.getIdByUserName(customerName);
        return cartItemDao.findIdsByCustomerId(customerId);
    }
}

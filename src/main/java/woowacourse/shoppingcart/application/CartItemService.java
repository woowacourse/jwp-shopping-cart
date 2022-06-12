package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.OptionalLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CartItemRepository;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.dto.CartItemQuantityUpdateRequest;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.CartItemSaveRequest;
import woowacourse.shoppingcart.dto.customer.LoginCustomer;
import woowacourse.shoppingcart.exception.InvalidCustomerException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartItemService {

    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final CartItemRepository cartItemRepository;

    public CartItemService(CartItemDao cartItemDao, CustomerDao customerDao, CartItemRepository cartItemRepository) {
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.cartItemRepository = cartItemRepository;
    }

    public List<CartItemResponse> findCartsByCustomerName(LoginCustomer loginCustomer) {
        Long customerId = customerDao.findIdByUsername(loginCustomer.getUsername())
                .orElseThrow(InvalidCustomerException::new);
        List<CartItem> cartItems = cartItemRepository.findCartItemsByCustomerId(customerId);
        return cartItems.stream()
                .map(CartItemResponse::new)
                .collect(Collectors.toList());
    }

    public Long addCart(CartItemSaveRequest cartItemSaveRequest, LoginCustomer loginCustomer) {
        Long customerId = customerDao.findIdByUsername(loginCustomer.getUsername())
                .orElseThrow(InvalidCustomerException::new);
        OptionalLong OptionalCartItemId = cartItemDao.findIdByProductId(cartItemSaveRequest.getProductId());
        if (OptionalCartItemId.isPresent()) {
            Long cartItemId = OptionalCartItemId.getAsLong();
            cartItemDao.addQuantity(cartItemId, cartItemSaveRequest.getQuantity());
            return cartItemId;
        }
        return cartItemDao.addCartItem(customerId, cartItemSaveRequest.getProductId(),
                cartItemSaveRequest.getQuantity());
    }

    public void deleteCart(LoginCustomer loginCustomer, Long cartItemId) {
        validateCustomerCart(cartItemId, loginCustomer.getUsername());
        cartItemDao.deleteCartItem(cartItemId);
    }

    private void validateCustomerCart(Long cartItemId, String username) {
        Long customerId = customerDao.findIdByUsername(username)
                .orElseThrow(InvalidCustomerException::new);
        List<Long> cartIds = cartItemDao.findIdsByCustomerId(customerId);
        if (cartIds.contains(cartItemId)) {
            return;
        }
        throw new NotInCustomerCartItemException();
    }

    public void updateQuantity(LoginCustomer loginCustomer, Long cartItemId,
            CartItemQuantityUpdateRequest cartItemQuantityUpdateRequest) {
        customerDao.findIdByUsername(loginCustomer.getUsername());
        cartItemDao.updateQuantity(cartItemId, cartItemQuantityUpdateRequest.getQuantity());
    }
}

package woowacourse.shoppingcart.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.CartItemSaveRequest;
import woowacourse.shoppingcart.dto.customer.LoginCustomer;
import woowacourse.shoppingcart.exception.InvalidCartItemException;
import woowacourse.shoppingcart.exception.NoSuchCartItemException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartItemService {

    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public CartItemService(CartItemDao cartItemDao, CustomerDao customerDao, ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    public List<CartItemResponse> findCartsByCustomerName(LoginCustomer loginCustomer) {
        List<Long> cartIds = findCartIdsByCustomerName(loginCustomer.getUsername());

        List<CartItem> cartItems = new ArrayList<>();
        for (Long cartId : cartIds) {
            Long productId = cartItemDao.findProductIdById(cartId)
                    .orElseThrow(InvalidCartItemException::new);
            Product product = productDao.findProductById(productId);
            Integer quantity = cartItemDao.findQuantityById(cartId)
                    .orElseThrow(NoSuchCartItemException::new);
            cartItems.add(new CartItem(cartId, product, quantity));
        }
        return cartItems.stream()
                .map(CartItemResponse::new)
                .collect(Collectors.toList());
    }

    private List<Long> findCartIdsByCustomerName(String customerName) {
        Long customerId = customerDao.findIdByUsername(customerName);
        return cartItemDao.findIdsByCustomerId(customerId);
    }

    public Long addCart(CartItemSaveRequest cartItemSaveRequest, LoginCustomer loginCustomer) {
        Long customerId = customerDao.findIdByUsername(loginCustomer.getUsername());
        Optional<Long> OptionalCartItemId = cartItemDao.findIdByProductId(cartItemSaveRequest.getProductId());
        if (OptionalCartItemId.isPresent()) {
            Long cartItemId = OptionalCartItemId.get();
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
        List<Long> cartIds = findCartIdsByCustomerName(username);
        if (cartIds.contains(cartItemId)) {
            return;
        }
        throw new NotInCustomerCartItemException();
    }

    public void updateQuantity(LoginCustomer loginCustomer, Long cartItemId, int quantity) {
        customerDao.findIdByUsername(loginCustomer.getUsername());
        cartItemDao.updateQuantity(cartItemId, quantity);
    }
}

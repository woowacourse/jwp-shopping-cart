package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.dto.CartItemsResponse;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.*;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

@Service
public class CartService {

    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public CartService(CartItemDao cartItemDao, CustomerDao customerDao, ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    @Transactional
    public void addCart(CartProductRequest cartProductRequest, String customerName) {
        Long customerId = customerDao.findByUsername(customerName).getId();

        Product product = productDao.findProductById(cartProductRequest.getProductId());
        if (cartItemDao.existByCustomerIdAndProductId(customerId, cartProductRequest.getProductId())) {
            CartItem cartItem = cartItemDao.findIdByCustomerIdAndProductId(customerId, product);
            cartItemDao.updateById(cartItem.getId(), customerId, cartItem.getQuantity() + cartProductRequest.getQuantity(), cartItem.isChecked());
            return;
        }
        cartItemDao.addCartItem(customerId, cartProductRequest.getProductId(), cartProductRequest.getQuantity(), cartProductRequest.isChecked());
    }

    @Transactional(readOnly = true)
    public CartItemsResponse findCart(String customerName) {
        List<CartItem> cartItems = findCartIdsByCustomerName(customerName).stream()
                .map(cartItemDao::findCartIdById)
                .collect(Collectors.toList());
        return CartItemsResponse.from(cartItems);
    }

    private List<Long> findCartIdsByCustomerName(String customerName) {
        Long customerId = customerDao.findByUsername(customerName).getId();
        return cartItemDao.findIdsByCustomerId(customerId);
    }

    @Transactional
    public CartItemsResponse updateCartItems(String customerName, UpdateCartItemsRequest updateCartItemsRequest) {
        Long customerId = customerDao.findByUsername(customerName).getId();
        List<CartItem> cartItems = updateCartItemsRequest.getCartItems()
                .stream()
                .map(request -> new CartItem(request.getId(), cartItemDao.findCartIdById(request.getId()).getCustomerId(),
                        cartItemDao.findCartIdById(request.getId()).getProduct(),
                        request.getQuantity(), request.isChecked()))
                .collect(Collectors.toList());

        validateUpdateCartItem(customerId, cartItems);

        cartItemDao.updateByIds(customerId, cartItems);

        return findUpdateCartProducts(updateCartItemsRequest);
    }

    private void validateUpdateCartItem(Long customerId, List<CartItem> cartItems) {
        for (CartItem cartItem : cartItems) {
            if (!cartItem.getCustomerId().equals(customerId)) {
                throw new InvalidCartItemException();
            }
        }
    }

    private CartItemsResponse findUpdateCartProducts(UpdateCartItemsRequest updateCartItemsRequest) {
        List<CartItem> cartItems = updateCartItemsRequest
                .getCartItems()
                .stream()
                .map(item -> cartItemDao.findCartIdById(item.getId()))
                .collect(Collectors.toList());
        return CartItemsResponse.from(cartItems);
    }

    @Transactional
    public void deleteCart(String customerName, DeleteCartItemRequest deleteCartItemRequest) {
        Long customerId = customerDao.findByUsername(customerName).getId();
        List<CartItem> cartItems = deleteCartItemRequest.getCartItems()
                .stream()
                .map(request -> cartItemDao.findCartIdById(request.getId()))
                .collect(Collectors.toList());
        validateUpdateCartItem(customerId, cartItems);

        List<Long> cartItemIds = cartItems.stream()
                .map(CartItem::getId)
                .collect(Collectors.toList());
        cartItemDao.deleteCartItemById(cartItemIds, customerId);
    }

    @Transactional
    public void deleteAll(String customerName) {
        Long customerId = customerDao.findByUsername(customerName).getId();
        cartItemDao.deleteAll(customerId);
    }
}

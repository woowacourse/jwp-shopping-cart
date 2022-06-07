package woowacourse.shoppingcart.application;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.application.dto.request.CustomerIdentificationRequest;
import woowacourse.shoppingcart.application.dto.request.ProductIdRequest;
import woowacourse.shoppingcart.application.dto.response.CartItemResponse;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;
import woowacourse.shoppingcart.exception.datanotfound.CartItemDataNotFoundException;
import woowacourse.shoppingcart.exception.datanotfound.CustomerDataNotFoundException;
import woowacourse.shoppingcart.exception.datanotfound.ProductDataNotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CartService {

    private final int DEFAULT_CART_ITEM_QUANTITY = 1;

    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public CartService(final CartItemDao cartItemDao, final CustomerDao customerDao, final ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    // TODO: delete
    public List<Cart> findCartsByCustomerName(final String customerName) {
        final List<Long> cartIds = findCartIdsByCustomerName(customerName);

        final List<Cart> carts = new ArrayList<>();
        for (final Long cartId : cartIds) {
            final Long productId = cartItemDao.findProductIdById(cartId);
            final Product product = productDao.findById(productId).get();
            carts.add(new Cart(cartId, product));
        }
        return carts;
    }

    public List<Cart> findCartsByCustomerId(final Long customerId) {
        List<Long> cartIds = findCartIdsByCustomerId(customerId);

        List<Cart> carts = new ArrayList<>();
        for (Long cartId : cartIds) {
            Long productId = cartItemDao.findProductIdById(cartId);
            Product product = findProductById(productId);
            carts.add(new Cart(cartId, product));
        }
        return Collections.unmodifiableList(carts);
    }

    // TODO: delete
    private List<Long> findCartIdsByCustomerName(final String customerName) {
        final Long customerId = customerDao.findIdByUserName(customerName);
        return cartItemDao.findIdsByCustomerId(customerId);
    }

    private List<Long> findCartIdsByCustomerId(final Long customerId) {
        Customer customer = findCustomerById(customerId);
        return cartItemDao.findIdsByCustomerId(customer.getId());
    }

    @Transactional
    public List<CartItemResponse> addCartItems(final CustomerIdentificationRequest customerIdentificationRequest, final List<ProductIdRequest> productIdRequests) {
        Customer customer = findCustomerById(customerIdentificationRequest.getId());

        List<CartItem> cartItems = new ArrayList<>();
        for (ProductIdRequest productIdRequest : productIdRequests) {
            Long productId = productIdRequest.getId();
            cartItems.add(addCartItem(customer, productId));
        }
        return CartItemResponse.from(cartItems);
    }

    @Transactional
    public void deleteCart(final String customerName, final Long cartId) {
        validateCustomerCart(cartId, customerName);
        cartItemDao.deleteCartItem(cartId);
    }

    private CartItem addCartItem(final Customer customer, final Long productId) {
        try {
            Long cartItemId = cartItemDao.addCartItem(customer.getId(), productId, DEFAULT_CART_ITEM_QUANTITY);
            return findCartItemById(cartItemId);
        } catch (DataIntegrityViolationException exception) {
            throw new ProductDataNotFoundException("존재하지 않는 상품입니다.");
        }
    }

    private Customer findCustomerById(final Long customerId) {
        return customerDao.findById(customerId)
                .orElseThrow(() -> new CustomerDataNotFoundException("존재하지 않는 회원입니다."));
    }

    private Product findProductById(final Long productId) {
        return productDao.findById(productId)
                .orElseThrow(() -> new ProductDataNotFoundException("존재하지 않는 상품입니다."));
    }

    private CartItem findCartItemById(final Long cartItemId) {
        return cartItemDao.findCartItemById(cartItemId)
                .orElseThrow(() -> new CartItemDataNotFoundException("존재하지 않는 장바구니 정보입니다."));
    }

    private void validateCustomerCart(final Long cartId, final String customerName) {
        final List<Long> cartIds = findCartIdsByCustomerName(customerName);
        if (cartIds.contains(cartId)) {
            return;
        }
        throw new NotInCustomerCartItemException();
    }
}

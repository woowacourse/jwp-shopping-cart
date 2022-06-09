package woowacourse.shoppingcart.application;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.application.dto.request.CartItemIdRequest;
import woowacourse.shoppingcart.application.dto.request.CartItemRequest;
import woowacourse.shoppingcart.application.dto.request.CustomerIdentificationRequest;
import woowacourse.shoppingcart.application.dto.request.ProductIdRequest;
import woowacourse.shoppingcart.application.dto.response.CartItemResponse;
import woowacourse.shoppingcart.application.dto.response.CartResponse;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.exception.dataformat.QuantityDataFormatException;
import woowacourse.shoppingcart.exception.datanotfound.CartItemDataNotFoundException;
import woowacourse.shoppingcart.exception.datanotfound.CustomerDataNotFoundException;
import woowacourse.shoppingcart.exception.datanotfound.ProductDataNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CartService {

    private static final int MINIMUM_QUANTITY = 1;

    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public CartService(final CartItemDao cartItemDao, final CustomerDao customerDao, final ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    public List<CartResponse> findCarts(final CustomerIdentificationRequest customerIdentificationRequest) {
        List<Long> cartIds = cartItemDao.findIdsByCustomerId(customerIdentificationRequest.getId());

        List<Cart> carts = new ArrayList<>();
        for (Long cartId : cartIds) {
            CartItem cartItem = findCartItemById(cartId);
            Product product = findProductById(cartItem.getProductId());
            carts.add(Cart.from(cartItem, product));
        }
        return CartResponse.from(carts);
    }

    @Transactional
    public List<CartItemResponse> addCartItems(final CustomerIdentificationRequest customerIdentificationRequest,
                                               final List<ProductIdRequest> productIdRequests) {
        Customer customer = findCustomerById(customerIdentificationRequest.getId());

        List<CartItem> cartItems = new ArrayList<>();
        for (ProductIdRequest productIdRequest : productIdRequests) {
            Long productId = productIdRequest.getId();
            cartItems.add(addCartItem(customer, productId));
        }
        return CartItemResponse.from(cartItems);
    }

    @Transactional
    public CartItemResponse updateQuantity(final CustomerIdentificationRequest customerIdentificationRequest,
                                                 final CartItemRequest cartItemRequest) {
        validateQuantity(cartItemRequest.getQuantity());
        cartItemDao.updateQuantity(cartItemRequest.getId(), customerIdentificationRequest.getId(), cartItemRequest.getQuantity());
        return CartItemResponse.from(findCartItemById(cartItemRequest.getId()));
    }

    @Transactional
    public void deleteCarts(final List<CartItemIdRequest> cartItemIdRequests) {
        for (CartItemIdRequest cartItemIdRequest : cartItemIdRequests) {
            cartItemDao.delete(cartItemIdRequest.getId());
        }
    }

    private CartItem addCartItem(final Customer customer, final Long productId) {
        try {
            Long cartItemId = cartItemDao.addCartItem(customer.getId(), productId, MINIMUM_QUANTITY);
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

    private void validateQuantity(final int quantity) {
        if (quantity < MINIMUM_QUANTITY) {
            throw new QuantityDataFormatException("수량은 1 이상이어야 합니다.");
        }
    }
}

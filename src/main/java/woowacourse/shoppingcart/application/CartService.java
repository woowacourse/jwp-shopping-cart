package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.domain.user.Customer;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.QuantityRequest;
import woowacourse.shoppingcart.exception.badrequest.DuplicateCartItemException;
import woowacourse.shoppingcart.exception.badrequest.InvalidProductException;
import woowacourse.shoppingcart.exception.badrequest.InvalidProductIdException;
import woowacourse.shoppingcart.exception.badrequest.NotInCustomerCartItemException;
import woowacourse.shoppingcart.exception.notfound.NotFoundProductException;
import woowacourse.shoppingcart.exception.unauthorized.UnauthorizedException;

@Service
@Transactional
public class CartService {

    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public CartService(final CartItemDao cartItemDao, final CustomerDao customerDao, final ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    public List<CartItemResponse> findCartsByCustomerEmail(final String email) {
        Long customerId = findCustomerByEmail(email)
                .getId();
        List<CartItem> cartItems = cartItemDao.findByCustomerId(customerId);
        return cartItems.stream()
                .map(cartItem -> new CartItemResponse(cartItem.getProductId(), cartItem.getName(), cartItem.getPrice(),
                        cartItem.getImageUrl(), cartItem.getQuantity()))
                .collect(Collectors.toList());
    }

    public Long addCart(Long productId, String email) {
        Customer customer = findCustomerByEmail(email);
        Product product = findProductById(productId, InvalidProductIdException::new);
        List<CartItem> cartItems = cartItemDao.findByCustomerId(customer.getId());
        validateDuplicateProduct(product, cartItems);
        try {
            return cartItemDao.addCartItem(customer.getId(), product.getId());
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    private void validateDuplicateProduct(Product product, List<CartItem> cartItems) {
        if (cartItems.stream()
                .anyMatch(cartItem -> cartItem.hasProductById(product.getId()))) {
            throw new DuplicateCartItemException();
        }
    }

    public void deleteCart(final String email, final Long productId) {
        Customer customer = findCustomerByEmail(email);
        Product product = findProductById(productId, NotFoundProductException::new);
        List<CartItem> cartItems = cartItemDao.findByCustomerId(customer.getId());
        CartItem deletedCartItem = findCartItemByProductId(product.getId(), cartItems);
        cartItemDao.deleteCartItem(deletedCartItem.getId());
    }

    private Customer findCustomerByEmail(String email) {
        return customerDao.findByEmail(email)
                .orElseThrow(UnauthorizedException::new);
    }

    private Product findProductById(Long productId, Supplier<RuntimeException> exceptionSupplier) {
        return productDao.findProductById(productId)
                .orElseThrow(exceptionSupplier);
    }

    private CartItem findCartItemByProductId(Long productId, List<CartItem> cartItems) {
        return cartItems.stream()
                .filter(cartItem -> cartItem.hasProductById(productId))
                .findAny()
                .orElseThrow(NotInCustomerCartItemException::new);
    }

    public CartItemResponse updateQuantity(String email, Long productId, QuantityRequest quantityRequest) {
        Customer customer = findCustomerByEmail(email);
        Product product = findProductById(productId, NotFoundProductException::new);
        List<CartItem> cartItems = cartItemDao.findByCustomerId(customer.getId());
        CartItem cartItem = findCartItemByProductId(product.getId(), cartItems);
        CartItem updateCartItem = cartItem.updateQuantity(quantityRequest.getQuantity());
        cartItemDao.update(updateCartItem);
        return new CartItemResponse(updateCartItem.getId(), updateCartItem.getName(), updateCartItem.getPrice(),
                updateCartItem.getImageUrl(), updateCartItem.getQuantity());
    }
}

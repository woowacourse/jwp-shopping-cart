package woowacourse.shoppingcart.cartitem.application;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.exception.dto.ErrorResponse;
import woowacourse.shoppingcart.cartitem.dao.CartItemDao;
import woowacourse.shoppingcart.customer.dao.CustomerDao;
import woowacourse.shoppingcart.product.dao.ProductDao;
import woowacourse.shoppingcart.cartitem.domain.CartItem;
import woowacourse.shoppingcart.customer.domain.Customer;
import woowacourse.shoppingcart.product.domain.Product;
import woowacourse.shoppingcart.cartitem.dto.CartItemResponse;
import woowacourse.shoppingcart.exception.AlreadyExistException;
import woowacourse.shoppingcart.exception.InvalidCartItemException;
import woowacourse.shoppingcart.exception.NotExistException;

@Service
@Transactional(readOnly = true)
public class CartItemService {

    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public CartItemService(final CartItemDao cartItemDao, final CustomerDao customerDao, final ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    @Transactional
    public CartItemResponse addCartItem(Long productId, Integer quantity, String email) {
        final Customer customer = customerDao.findByEmail(email);
        if (cartItemDao.existsByProductIdAndCustomerId(productId, customer.getId())) {
            throw new AlreadyExistException("이미 장바구니에 담겨 있는 상품입니다.", ErrorResponse.ALREADY_EXIST_PRODUCT_IN_CART);
        }

        final Product product = productDao.findProductById(productId);
        final Long id = cartItemDao.addCartItem(customer.getId(), productId, quantity);
        return CartItemResponse.of(id, quantity, product);
    }

    public List<CartItemResponse> findCartItems(String email) {
        final Customer customer = customerDao.findByEmail(email);
        final List<CartItem> cartItems = cartItemDao.findAllByCustomerId(customer.getId());
        return cartItems.stream()
                .map(cartItem -> CartItemResponse.of(cartItem.getId(), cartItem.getQuantity(), cartItem.getProduct()))
                .collect(Collectors.toUnmodifiableList());
    }

    public CartItemResponse findCartItem(String email, Long cartItemId) {
        final Customer customer = customerDao.findByEmail(email);
        final CartItem cartItem = cartItemDao.findByCustomerId(customer.getId(), cartItemId);
        return CartItemResponse.of(cartItem.getId(), cartItem.getQuantity(), cartItem.getProduct());
    }

    @Transactional
    public void updateQuantity(String email, Long cartItemId, Integer quantity) {
        final Customer customer = customerDao.findByEmail(email);
        if (cartItemDao.notExistByIdAndCustomerId(cartItemId, customer.getId())) {
            throw new NotExistException("존재하지 않는 장바구니 "
                    + "아이템입니다.", ErrorResponse.NOT_EXIST_CART_ITEM);
        }
        cartItemDao.updateQuantityByCustomerId(customer.getId(), cartItemId, quantity);
    }

    @Transactional
    public void deleteCartItem(String email, List<Long> cartItemIds) {
        final Customer customer = customerDao.findByEmail(email);
        validateCustomerCart(cartItemIds, customer.getId());

        cartItemDao.deleteCartItemByIds(cartItemIds);
    }

    private void validateCustomerCart(List<Long> cartItemIds, Long customerId) {
        final List<CartItem> cartItems = cartItemDao.findAllByCustomerId(customerId);
        final Set<Long> savedCartItemIds = new HashSet<>(cartItems.stream()
                .map(CartItem::getId)
                .collect(Collectors.toUnmodifiableList()));
        if (containsAllIds(cartItemIds, savedCartItemIds)) {
            return;
        }
        throw new InvalidCartItemException("유효하지 않는 장바구니 아이템입니다.", ErrorResponse.INVALID_CART_ITEM);
    }

    private boolean containsAllIds(List<Long> cartItemIds, Set<Long> savedCartItemIds) {
        return cartItemIds.stream()
                .filter(it -> savedCartItemIds.contains(it))
                .limit(savedCartItemIds.size())
                .count() == savedCartItemIds.size();
    }
}

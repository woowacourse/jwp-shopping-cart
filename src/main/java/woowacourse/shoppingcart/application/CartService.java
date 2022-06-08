package woowacourse.shoppingcart.application;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.application.dto.CartItemQuantityUpdateRequest;
import woowacourse.shoppingcart.application.dto.CartItemResponse;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public CartService(final CartItemDao cartItemDao, final CustomerDao customerDao, final ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    public Long saveCartItem(final Long productId, final Long customerId) {
        productDao.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        validateDuplicatedCartItems(customerId, productId);
        return cartItemDao.saveCartItem(customerId, productId);
    }

    private void validateDuplicatedCartItems(Long customerId, Long productId) {
        if (cartItemDao.existCartItems(customerId, productId)) {
            throw new IllegalArgumentException("이미 등록되어 있는 상품입니다.");
        }
    }

    public List<CartItemResponse> findAllByCustomerId(Long customerId) {
        List<CartItem> cartProducts = cartItemDao.findAllByCustomerId(customerId);

        return cartProducts.stream()
                .map(it -> new CartItemResponse(it.getId(), it.getName(), it.getPrice(), it.getQuantity(),
                        it.getImageUrl()))
                .collect(Collectors.toList());
    }

    public void updateQuantity(CartItemQuantityUpdateRequest request) {
        productDao.findById(request.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        validateQuantity(request.getQuantity());
        cartItemDao.updateQuantity(request.getCustomerId(), request.getProductId(), request.getQuantity());
    }

    private void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException();
        }
    }

    public List<Cart> findCartsByCustomerName(final String customerName) {
        final List<Long> cartIds = findCartIdsByCustomerName(customerName);

        final List<Cart> carts = new ArrayList<>();
        for (final Long cartId : cartIds) {
            Long productId = cartItemDao.findProductIdById(cartId);
            Product product = productDao.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
            carts.add(new Cart(cartId, product));
        }
        return carts;
    }

    private List<Long> findCartIdsByCustomerName(final String customerName) {
        final Long customerId = customerDao.findIdByUserName(customerName);
        return cartItemDao.findIdsByCustomerId(customerId);
    }

    public void deleteCartItem(Long customerId, Long productId) {
        cartItemDao.deleteCartItem(customerId, productId);
    }

    private void validateCustomerCart(final Long cartId, final String customerName) {
        final List<Long> cartIds = findCartIdsByCustomerName(customerName);
        if (cartIds.contains(cartId)) {
            return;
        }
        throw new NotInCustomerCartItemException();
    }
}

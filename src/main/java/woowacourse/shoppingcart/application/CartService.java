package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.application.dto.CartItemQuantityUpdateRequest;
import woowacourse.shoppingcart.application.dto.CartItemResponse;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.CartItem;

@Transactional(readOnly = true)
@Service
public class CartService {

    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public CartService(final CartItemDao cartItemDao, final CustomerDao customerDao, final ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    @Transactional
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

    @Transactional
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

    @Transactional
    public void deleteCartItem(Long customerId, Long productId) {
        cartItemDao.deleteCartItem(customerId, productId);
    }
}

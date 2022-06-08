package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.ProductIdsRequest;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.exception.InvalidProductException;

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

    public List<ProductResponse> findCartProductByCustomerId(final Long customerId) {
        final List<Long> cartIds = cartItemDao.findIdsByCustomerId(customerId);

        return cartIds.stream()
                .map(this::findProductRequestByCartId)
                .collect(Collectors.toList());
    }

    private ProductResponse findProductRequestByCartId(Long cartId) {
        Long productId = cartItemDao.findProductIdById(cartId);
        Product product = productDao.findProductById(productId);
        Integer quantity = cartItemDao.findQuantityById(cartId);
        return new ProductResponse(product, quantity);
    }

    public Long addCart(final Long customerId, final CartRequest cartRequest) {
        try {
            validateQuantity(cartRequest.getQuantity());
            return cartItemDao.addCartItem(customerId, cartRequest.getId(), cartRequest.getQuantity());
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    private void validateQuantity(Integer quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("올바르지 않은 상품 수량 형식입니다.");
        }
    }

    public void deleteCart(final Long customerId, final ProductIdsRequest productIds) {
        for (Long productId : productIds.getProductIds()) {
            cartItemDao.deleteCartItem(customerId, productId);
        }
    }

    public void updateCartQuantity(Long customerId, CartRequest cartRequest) {
        cartItemDao.updateQuantity(customerId, cartRequest.getId(), cartRequest.getQuantity());
    }
}

package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartsDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartsDao cartsDao;
    private final ProductDao productDao;

    public CartService(final CartsDao cartsDao, final ProductDao productDao) {
        this.cartsDao = cartsDao;
        this.productDao = productDao;
    }

    @Transactional(readOnly = true)
    public List<CartResponse> findCartsById(final Long id) {
        return cartsDao.findCartsByMemberId(id).stream()
                .map(CartResponse::from)
                .collect(Collectors.toList());
    }

    public Long addCart(final Long memberId, final CartRequest cartRequest) {
        final Product foundProduct = productDao.findProductById(cartRequest.getProductId());
        final Cart cart = new Cart(memberId, foundProduct, cartRequest.getQuantity());
        return cartsDao.save(cart);
    }

    public void deleteCart(final Long memberId, final Long cartId) {
        validateCustomerCart(memberId, cartId);
        cartsDao.delete(cartId);
    }

    private void validateCustomerCart(final Long memberId, final Long cartId) {
        final List<Cart> cart = cartsDao.findCartsByMemberId(memberId);
        final Cart foundCart = cartsDao.findCartById(cartId);
        if (!cart.contains(foundCart)) {
            throw new NotInCustomerCartItemException();
        }
    }
}

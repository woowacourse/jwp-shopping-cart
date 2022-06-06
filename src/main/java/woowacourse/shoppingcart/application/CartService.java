package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartsDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Carts;
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
        final Carts carts = new Carts(memberId, foundProduct, cartRequest.getQuantity());
        return cartsDao.save(carts);
    }

    public void deleteCart(final Long memberId, final Long cartId) {
        validateCustomerCart(memberId, cartId);
        cartsDao.delete(cartId);
    }

    private void validateCustomerCart(final Long memberId, final Long cartId) {
        final List<Carts> carts = cartsDao.findCartsByMemberId(memberId);
        final Carts foundCart = cartsDao.findCartById(cartId);
        if (!carts.contains(foundCart)) {
            throw new NotInCustomerCartItemException();
        }
    }
}

package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Amount;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.cart.CartItemCreateRequest;
import woowacourse.shoppingcart.dto.cart.CartItemDto;
import woowacourse.shoppingcart.exception.ExistSameProductIdException;
import woowacourse.shoppingcart.exception.NoSuchProductException;
import woowacourse.shoppingcart.exception.OutOfStockException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartItemService {

    private final CartItemDao cartItemDao;
    private final ProductDao productDao;

    public CartItemService(final CartItemDao cartItemDao, final ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.productDao = productDao;
    }

    public List<CartItemDto> findCartsByCustomerId(final Long customerId) {
        List<CartItem> cartItems = cartItemDao.findCartItemsByCustomerId(customerId);

        return cartItems.stream()
                .map(item -> new CartItemDto(productDao.getProductById(item.getProductId()), item.getCount()))
                .collect(Collectors.toList());
    }

    public Long addCartItem(final Long customerId, final CartItemCreateRequest request) {
        if (cartItemDao.existIdByCustomerIdAndProductId(customerId, request.getProductId())) {
            throw new ExistSameProductIdException();
        }

        Product foundProduct = productDao.getProductById(request.getProductId());
        validateCount(foundProduct, request.getCount());

        return cartItemDao.addCartItem(customerId, request);
    }

    private void validateCount(Product product, int count) {
        if (product.isAvailable(new Amount(count))) {
            return;
        }

        throw new OutOfStockException();
    }

    public void updateCount(final Long customerId, final Long productId, final int newCount) {
        Product foundProduct = productDao.getProductById(productId);
        validateCount(foundProduct, newCount);

        cartItemDao.updateCount(customerId, productId, newCount);
    }

    public void deleteCartItem(final Long customerId, final Long productId) {
        validateProductInCustomerCart(customerId, productId);
        cartItemDao.deleteCartItemByCustomerIdAndProductId(customerId, productId);
    }

    private void validateProductInCustomerCart(final Long customerId, final Long productId) {
        Cart cart = new Cart(cartItemDao.findCartItemsByCustomerId(customerId));

        if (cart.containsProductId(productId)) {
            return;
        }

        throw new NoSuchProductException("장바구니에 해당 상품이 존재하지 않습니다.");
    }
}

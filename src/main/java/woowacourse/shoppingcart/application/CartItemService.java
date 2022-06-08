package woowacourse.shoppingcart.application;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.cart.CartItemCreateRequest;
import woowacourse.shoppingcart.dto.cart.CartItemDto;
import woowacourse.shoppingcart.exception.ExistSameProductIdException;
import woowacourse.shoppingcart.exception.NoSuchProductException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;
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

        final List<CartItemDto> cartItemDtos = new ArrayList<>();

        for (final CartItem cartItem : cartItems) {
            final Product product = productDao.findProductById(cartItem.getProductId());
            cartItemDtos.add(new CartItemDto(product, cartItem.getCount()));
        }

        return cartItemDtos;
    }

    public Long addCartItem(final Long customerId, final CartItemCreateRequest request) {
        if (cartItemDao.existIdByCustomerIdAndProductId(customerId, request.getProductId())) {
            throw new ExistSameProductIdException();
        }

        Integer quantity = productDao.findProductById(request.getProductId()).getQuantity();
        if (quantity < request.getCount()) {
            throw new OutOfStockException();
        }

        try {
            return cartItemDao.addCartItem(customerId, request);
        } catch (Exception e) {
            throw new NoSuchProductException();
        }
    }

    public void deleteCart(final Long customerId, final Long cartId) {
        validateCustomerCart(cartId, customerId);
        cartItemDao.deleteCartItem(cartId);
    }

    private void validateCustomerCart(final Long cartId, final Long customerId) {
        List<CartItem> cartItemsByCustomerId = cartItemDao.findCartItemsByCustomerId(customerId);
        List<Long> cartItemIds = cartItemsByCustomerId.stream()
                .map(CartItem::getId)
                .collect(Collectors.toList());

        if (cartItemIds.contains(cartId)) {
            return;
        }

        throw new NotInCustomerCartItemException();
    }
}

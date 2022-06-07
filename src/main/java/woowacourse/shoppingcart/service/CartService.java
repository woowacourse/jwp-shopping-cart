package woowacourse.shoppingcart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.request.AddCartItemRequestDto;
import woowacourse.shoppingcart.dto.response.ProductResponseDto;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;
    private final ProductDao productDao;

    public CartService(final CartItemDao cartItemDao, final ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.productDao = productDao;
    }

    public List<ProductResponseDto> findCartsByCustomerId(final Long customerId) {
        final List<CartItem> cartItems = cartItemDao.findCartItemsByCustomerId(customerId);

        return cartItems.stream()
                .map(cartItem -> new ProductResponseDto(
                        cartItem.getProductId(),
                        cartItem.getImageUrl(),
                        cartItem.getName(),
                        cartItem.getPrice(),
                        cartItem.getQuantity(),
                        cartItem.getCount())
                ).collect(Collectors.toList());
    }

    public Long addCart(final AddCartItemRequestDto addCartItemRequestDto, final Long customerId) {
        try {
            return cartItemDao.addCartItem(
                    customerId,
                    addCartItemRequestDto.getProductId(),
                    addCartItemRequestDto.getCount());
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    public void deleteCart(final Long customerId, final Long productId) {
        validateCustomerCart(productId, customerId);
        cartItemDao.deleteCartItem(customerId, productId);
    }

    private void validateCustomerCart(final Long cartId, final Long customerId) {
        final List<Long> cartIds = cartItemDao.findIdsByCustomerId(customerId);
        if (cartIds.contains(cartId)) {
            return;
        }
        throw new NotInCustomerCartItemException();
    }
}

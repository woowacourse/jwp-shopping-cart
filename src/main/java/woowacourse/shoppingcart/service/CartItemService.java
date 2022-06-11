package woowacourse.shoppingcart.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.request.AddCartItemRequestDto;
import woowacourse.shoppingcart.dto.request.UpdateCartItemCountItemRequest;
import woowacourse.shoppingcart.dto.response.CartItemResponseDto;
import woowacourse.shoppingcart.exception.DuplicateCartItemException;
import woowacourse.shoppingcart.exception.NotFoundProductException;
import woowacourse.shoppingcart.exception.OverQuantityException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartItemService {

    private final CartItemDao cartItemDao;
    private final ProductDao productDao;

    public CartItemService(final CartItemDao cartItemDao, final ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.productDao = productDao;
    }

    public List<CartItemResponseDto> findCartItemsByCustomerId(final Long customerId) {
        final List<CartItem> cartItems = cartItemDao.findCartItemsByCustomerId(customerId);

        return cartItems.stream()
                .map(cartItem -> new CartItemResponseDto(
                        cartItem.getProductId(),
                        cartItem.getImageUrl(),
                        cartItem.getName(),
                        cartItem.getPrice(),
                        cartItem.getQuantity(),
                        cartItem.getCount())
                ).collect(Collectors.toList());
    }

    public Long addCart(final AddCartItemRequestDto addCartItemRequestDto, final Long customerId) {
        checkDuplicateProduct(customerId, addCartItemRequestDto.getProductId());
        compareCountAndQuantity(addCartItemRequestDto.getCount(), addCartItemRequestDto.getProductId());
        try {
            return cartItemDao.addCartItem(
                    customerId,
                    addCartItemRequestDto.getProductId(),
                    addCartItemRequestDto.getCount());
        } catch (final DataIntegrityViolationException e) {
            throw new NotFoundProductException();
        }
    }

    private void compareCountAndQuantity(final Integer count, final Long productId) {
        Product product = productDao.findProductById(productId)
                .orElseThrow(NotFoundProductException::new);
        if (product.getQuantity() < count) {
            throw new OverQuantityException();
        }
    }

    private void checkDuplicateProduct(final Long customerId, final Long productId) {
        cartItemDao.findCartItemByCustomerIdAndProductId(customerId, productId)
                .ifPresent(cartItem -> {
                    throw new DuplicateCartItemException();
                });
    }

    public void deleteCart(final Long customerId, final Long productId) {
        cartItemDao.deleteCartItem(customerId, productId);
    }

    public void updateCart(final Long customerId, final Long productId, final UpdateCartItemCountItemRequest updateCartItemCountItemRequest) {
        compareCountAndQuantity(updateCartItemCountItemRequest.getCount(), productId);
        cartItemDao.updateCartItem(customerId, productId, updateCartItemCountItemRequest.getCount());
    }
}

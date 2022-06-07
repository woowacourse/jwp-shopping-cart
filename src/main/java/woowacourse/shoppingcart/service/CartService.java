package woowacourse.shoppingcart.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.request.CartItemRequest;
import woowacourse.shoppingcart.dto.response.CartItemResponse;
import woowacourse.shoppingcart.dto.response.ProductExistingInCartResponse;
import woowacourse.shoppingcart.entity.CartItemEntity;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;
    private final ProductDao productDao;

    public CartService(final CartItemDao cartItemDao, final ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.productDao = productDao;
    }

    public List<CartItemResponse> findCartItemsByCustomerId(final Long customerId) {
        List<CartItemEntity> cartItemEntities = cartItemDao.findCartItemsByCustomerId(customerId);
        List<CartItem> cartItems = cartItemEntities.stream()
                .map(this::convertEntityToCartItem)
                .collect(Collectors.toList());

        return cartItems.stream()
                .map(CartItemResponse::new)
                .collect(Collectors.toList());
    }

    private CartItem convertEntityToCartItem(CartItemEntity cartItemEntity) {
        Product product = productDao.findProductById(cartItemEntity.getProductId());
        return new CartItem(cartItemEntity.getId(), cartItemEntity.getCustomerId(), product,
                cartItemEntity.getQuantity());
    }

    public Long addCart(final CartItemRequest cartItemRequest, final Long customerId) {
        Product product = productDao.findProductById(cartItemRequest.getProductId());
        CartItem cartItem = new CartItem(customerId, product, cartItemRequest.getQuantity());

        try {
            return cartItemDao.addCartItem(customerId, cartItem);
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    public void deleteCart(final Long customerId, final Long cartId) {
        validateCustomerCart(cartId, customerId);
        cartItemDao.deleteCartItem(cartId);
    }

    public void updateCartItem(Long cartItemId, CartItemRequest cartItemRequest) {
        Long customerId = cartItemDao.findCartItemById(cartItemId).getCustomerId();
        Product newProduct = productDao.findProductById(cartItemRequest.getProductId());
        Integer newQuantity = cartItemRequest.getQuantity();
        CartItem newCartItem = new CartItem(cartItemId, customerId, newProduct, newQuantity);

        cartItemDao.updateCartItem(cartItemId, newCartItem);
    }

    private void validateCustomerCart(final Long cartId, final Long customerId) {
        final List<Long> cartIds = findCartItemIdsByCustomerId(customerId);
        if (cartIds.contains(cartId)) {
            return;
        }
        throw new NotInCustomerCartItemException();
    }

    private List<Long> findCartItemIdsByCustomerId(final Long customerId) {
        return cartItemDao.findCartItemsByCustomerId(customerId)
                .stream()
                .map(CartItemEntity::getId)
                .collect(Collectors.toList());
    }

    public ProductExistingInCartResponse isProductExisting(Long customerId, Long productId) {
        boolean isExisting = cartItemDao.isProductExisting(customerId, productId);
        return new ProductExistingInCartResponse(isExisting);
    }
}

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
        List<CartItemEntity> cartItemEntities = cartItemDao.findAllByCustomerId(customerId);
        List<CartItem> cartItems = cartItemEntities.stream()
                .map(this::convertEntityToCartItem)
                .collect(Collectors.toList());

        return cartItems.stream()
                .map(CartItemResponse::new)
                .collect(Collectors.toList());
    }

    private CartItem convertEntityToCartItem(CartItemEntity cartItemEntity) {
        Product product = productDao.findById(cartItemEntity.getProductId());
        return new CartItem(cartItemEntity.getId(), product, cartItemEntity.getQuantity());
    }

    public Long addCart(final CartItemRequest cartItemRequest, final Long customerId) {
        Product product = productDao.findById(cartItemRequest.getProductId());
        CartItem cartItem = new CartItem(customerId, product, cartItemRequest.getQuantity());

        try {
            return cartItemDao.save(customerId, cartItem);
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    public void deleteCart(final Long customerId, final Long cartItemId) {
        validateCustomerCart(cartItemId, customerId);
        cartItemDao.delete(cartItemId);
    }

    public void updateCartItem(Long customerId, Long cartItemId, CartItemRequest cartItemRequest) {
        validateCustomerCart(cartItemId, customerId);
        validateProductId(cartItemId, cartItemRequest.getProductId());

        Product newProduct = productDao.findById(cartItemRequest.getProductId());
        Integer newQuantity = cartItemRequest.getQuantity();
        CartItem newCartItem = new CartItem(cartItemId, newProduct, newQuantity);

        cartItemDao.update(cartItemId, newCartItem);
    }

    private void validateCustomerCart(final Long cartItemId, final Long customerId) {
        CartItemEntity cartItemEntity = cartItemDao.findById(cartItemId);
        if (!cartItemEntity.getCustomerId().equals(customerId)) {
            throw new NotInCustomerCartItemException();
        }
    }

    private void validateProductId(final Long cartItemId, final Long productId) {
        CartItemEntity cartItemEntity = cartItemDao.findById(cartItemId);
        if (!cartItemEntity.getProductId().equals(productId)) {
            throw new IllegalArgumentException("productId는 변경될 수 없습니다.");
        }
    }

    public ProductExistingInCartResponse isProductExisting(Long customerId, Long productId) {
        boolean isExisting = cartItemDao.isProductExisting(customerId, productId);
        return new ProductExistingInCartResponse(isExisting);
    }
}

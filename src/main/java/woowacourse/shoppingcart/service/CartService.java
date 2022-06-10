package woowacourse.shoppingcart.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.request.CartItemRequest;
import woowacourse.shoppingcart.dto.response.CartItemResponse;
import woowacourse.shoppingcart.dto.response.ProductExistingInCartResponse;
import woowacourse.shoppingcart.entity.CartItemEntity;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;
import woowacourse.shoppingcart.repository.ProductRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {
    private final CartItemDao cartItemDao;
    private final ProductRepository productRepository;

    public CartService(final CartItemDao cartItemDao, final ProductRepository productRepository) {
        this.cartItemDao = cartItemDao;
        this.productRepository = productRepository;
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
        Product product = productRepository.findById(cartItemEntity.getProductId());
        return new CartItem(cartItemEntity.getId(), product, cartItemEntity.getQuantity());
    }

    public Long addCart(final CartItemRequest cartItemRequest, final Long customerId) {
        Long productId = cartItemRequest.getProductId();
        validateAlreadyExistingProduct(customerId, productId);

        Product product = productRepository.findById(cartItemRequest.getProductId());
        CartItem cartItem = new CartItem(customerId, product, cartItemRequest.getQuantity());

        try {
            return cartItemDao.save(customerId, cartItem);
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    private void validateAlreadyExistingProduct(Long customerId, Long productId) {
        if (cartItemDao.isProductExisting(customerId, productId)) {
            throw new IllegalArgumentException("이미 장바구니에 담겨있는 제품입니다.");
        }
    }

    public void deleteCart(final Long customerId, final Long cartItemId) {
        validateCustomerCart(cartItemId, customerId);
        cartItemDao.delete(cartItemId);
    }

    public void updateCartItem(Long customerId, Long cartItemId, CartItemRequest cartItemRequest) {
        validateCustomerCart(cartItemId, customerId);
        validateProductId(cartItemId, cartItemRequest.getProductId());

        Product newProduct = productRepository.findById(cartItemRequest.getProductId());
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

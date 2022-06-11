package woowacourse.shoppingcart.application;

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
import woowacourse.shoppingcart.dto.response.ProductResponse;

@Service
@Transactional(readOnly = true)
public class CartService {

    private final CartItemDao cartItemDao;
    private final ProductDao productDao;

    public CartService(CartItemDao cartItemDao, ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.productDao = productDao;
    }

    @Transactional
    public Long addCartItem(Long memberId, CartItemRequest cartItemRequest) {
        Long productId = cartItemRequest.getProductId();
        Integer quantity = cartItemRequest.getQuantity();
        checkPurchasable(productId, quantity);
        if (cartItemDao.exists(memberId, productId)) {
            return addCartItemQuantity(memberId, productId, quantity);
        }
        return saveCartItem(memberId, productId, quantity);
    }

    private void checkPurchasable(Long productId, Integer quantity) {
        Product product = productDao.findProductById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
        product.validateStock(quantity);
    }

    private Long saveCartItem(Long memberId, Long productId, Integer quantity) {
        CartItem cartItem = new CartItem(memberId, productId, quantity);
        return cartItemDao.save(cartItem);
    }

    private Long addCartItemQuantity(Long memberId, Long productId, Integer quantity) {
        CartItem cartItem = findCartItem(memberId, productId);
        cartItem.add(quantity);
        cartItemDao.updateQuantity(memberId, productId, cartItem.getQuantity());
        return cartItem.getId();
    }

    private CartItem findCartItem(Long memberId, Long productId) {
        return cartItemDao.findByMemberIdAndProductId(memberId, productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
    }

    public List<CartItemResponse> findAll(Long memberId) {
        List<CartItem> cartItems = cartItemDao.findByMemberId(memberId);
        return cartItems.stream()
                .map(this::toCartItemResponse)
                .collect(Collectors.toUnmodifiableList());
    }

    private CartItemResponse toCartItemResponse(CartItem cartItem) {
        Product product = productDao.findProductById(cartItem.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
        return new CartItemResponse(new ProductResponse(product), cartItem.getQuantity());
    }

    @Transactional
    public void updateCartItemQuantity(Long memberId, CartItemRequest updateRequest) {
        Long productId = updateRequest.getProductId();
        Integer quantity = updateRequest.getQuantity();
        validateProductExist(memberId, productId);
        checkPurchasable(productId, quantity);
        cartItemDao.updateQuantity(memberId, updateRequest.getProductId(), updateRequest.getQuantity());
    }

    private void validateProductExist(Long memberId, Long productId) {
        if (!cartItemDao.exists(memberId, productId)) {
            throw new IllegalArgumentException("장바구니에 존재하지 않는 상품입니다.");
        }
    }

    @Transactional
    public void deleteCartItem(Long memberId, Long productId) {
        validateProductExist(memberId, productId);
        cartItemDao.deleteByMemberIdAndProductId(memberId, productId);
    }
}

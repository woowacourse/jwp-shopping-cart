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
        Product product = productDao.findProductById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
        if (cartItemDao.exists(memberId, productId)) {
            return addCartItemQuantity(memberId, product, quantity);
        }
        return saveCartItem(memberId, product, quantity);
    }

    private Long saveCartItem(Long memberId, Product product, Integer quantity) {
        CartItem cartItem = new CartItem(memberId, product, quantity);
        return cartItemDao.save(cartItem);
    }

    private Long addCartItemQuantity(Long memberId, Product product, Integer quantity) {
        CartItem cartItem = findCartItem(memberId, product.getId());
        cartItem.add(quantity);
        cartItemDao.updateQuantity(cartItem);
        return cartItem.getId();
    }

    private CartItem findCartItem(Long memberId, Long productId) {
        return cartItemDao.findByMemberIdAndProductId(memberId, productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
    }

    public List<CartItemResponse> findAll(Long memberId) {
        List<CartItem> cartItems = cartItemDao.findByMemberId(memberId);
        return cartItems.stream()
                .map(CartItemResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateCartItemQuantity(Long memberId, CartItemRequest updateRequest) {
        Long productId = updateRequest.getProductId();
        Integer quantity = updateRequest.getQuantity();
        CartItem cartItem = cartItemDao.findByMemberIdAndProductId(memberId, productId)
                .orElseThrow(() -> new IllegalArgumentException("장바구니에 존재하지 않는 상품입니다."));
        cartItem.replaceQuantity(quantity);
        cartItemDao.updateQuantity(cartItem);
    }

    private void validateExist(Long memberId, Long productId) {
        if (!cartItemDao.exists(memberId, productId)) {
            throw new IllegalArgumentException("장바구니에 존재하지 않는 상품입니다.");
        }
    }

    @Transactional
    public void deleteCartItem(Long memberId, Long productId) {
        validateExist(memberId, productId);
        cartItemDao.deleteByMemberIdAndProductId(memberId, productId);
    }
}

package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dto.CartItemAddRequest;
import woowacourse.shoppingcart.dto.CartItemQuantityUpdateRequest;
import woowacourse.shoppingcart.dto.CartItemResponse;

@Service
public class CartService {

    private final CartItemDao cartItemDao;
    private final ProductService productService;

    @Autowired
    public CartService(CartItemDao cartItemDao, ProductService productService) {
        this.cartItemDao = cartItemDao;
        this.productService = productService;
    }

    @Transactional
    public void addCartItem(long memberId, CartItemAddRequest cartItemAddRequest) {
        long productId = cartItemAddRequest.getProductId();
        int quantity = cartItemAddRequest.getQuantity();
        productService.validateProductId(productId);
        productService.validateStock(productId, quantity);
        addCart(memberId, productId, quantity);
    }

    private void addCart(long memberId, long productId, int quantity) {
        if (cartItemDao.isAlreadyInCart(memberId, productId)) {
            cartItemDao.increaseQuantity(memberId, productId, quantity);
            return;
        }
        cartItemDao.addCartItem(memberId, productId, quantity);
    }

    @Transactional(readOnly = true)
    public List<CartItemResponse> findAllCartItems(long memberId) {
        return cartItemDao.findAll(memberId)
                .stream()
                .map(CartItemResponse::new)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public List<CartItemResponse> updateCartItemQuantity(long memberId,
                                                         CartItemQuantityUpdateRequest cartItemQuantityUpdateRequest) {
        long productId = cartItemQuantityUpdateRequest.getProductId();
        int quantity = cartItemQuantityUpdateRequest.getQuantity();
        productService.validateProductId(productId);
        productService.validateStock(productId, quantity);
        cartItemDao.updateCartItemQuantity(memberId, productId, quantity);
        return findAllCartItems(memberId);
    }

    @Transactional
    public List<CartItemResponse> deleteCartItem(long memberId, long productId) {
        productService.validateProductId(productId);
        cartItemDao.deleteCartItem(memberId, productId);
        return findAllCartItems(memberId);
    }
}

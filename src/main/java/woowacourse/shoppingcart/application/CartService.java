package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
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
    public void addCart(long memberId, long productId, int quantity) {
        productService.validateProductId(productId);
        productService.validateStock(productId, quantity);
        addCartItem(memberId, productId, quantity);
    }

    private void addCartItem(long memberId, long productId, int quantity) {
        if (cartItemDao.isExistsMemberIdAndProductId(memberId, productId)) {
            cartItemDao.increaseQuantity(memberId, productId, quantity);
            return;
        }
        cartItemDao.addCartItem(memberId, productId, quantity);
    }

    @Transactional(readOnly = true)
    public List<CartItemResponse> findAll(long memberId) {
        return cartItemDao.findAll(memberId)
                .stream()
                .map(CartItemResponse::new)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public List<CartItemResponse> updateQuantity(long memberId, long productId, int quantity) {
        productService.validateProductId(productId);
        productService.validateStock(productId, quantity);
        cartItemDao.updateQuantity(memberId, productId, quantity);
        return findAll(memberId);
    }

    @Transactional
    public List<CartItemResponse> deleteCartItem(long memberId, long productId) {
        productService.validateProductId(productId);
        cartItemDao.delete(memberId, productId);
        return findAll(memberId);
    }
}

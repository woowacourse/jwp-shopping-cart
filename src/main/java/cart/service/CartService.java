package cart.service;

import cart.controller.dto.response.CartItemResponse;
import cart.database.dao.CartDao;
import cart.database.dao.ProductDao;
import cart.database.repository.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class CartService {

    private static final int DEFAULT_COUNT = 1;

    private final ProductDao productDao;
    private final CartDao cartDao;
    private final CartRepository cartRepository;

    public CartService(ProductDao productDao, CartDao cartDao, CartRepository cartRepository) {
        this.productDao = productDao;
        this.cartDao = cartDao;
        this.cartRepository = cartRepository;
    }

    @Transactional
    public void addCart(Long userId, Long productId) {
        if (!productDao.existById(productId)) {
            throw new IllegalStateException("제품 아이디가 없습니다.");
        }

        cartDao.create(userId, productId, DEFAULT_COUNT);
    }

    public List<CartItemResponse> findCartItems(Long userId) {
        return cartRepository.findCartsWithProductByUserId(userId);
    }

    @Transactional
    public void deleteCartItem(Long userId, Long cartId) {
        cartDao.deleteByUserIdAndCartId(userId, cartId);
    }
}

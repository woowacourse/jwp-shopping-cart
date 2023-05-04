package cart.service;

import cart.CartRepository;
import cart.controller.dto.response.CartItemResponse;
import cart.controller.dto.response.UserResponse;
import cart.dao.CartDao;
import cart.dao.ProductDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class CartService {

    private final ProductDao productDao;
    private final CartDao cartDao;
    private final CartRepository cartRepository;

    public CartService(ProductDao productDao, CartDao cartDao, CartRepository cartRepository) {
        this.productDao = productDao;
        this.cartDao = cartDao;
        this.cartRepository = cartRepository;
    }

    @Transactional
    public void addCart(UserResponse userResponse, Long productId) {
        if (!productDao.existById(productId)) {
            throw new IllegalStateException("제품 아이디가 없습니다.");
        }

        cartDao.create(userResponse.getId(), productId);
    }

    public List<CartItemResponse> findCartItemsByUser(UserResponse userResponse) {
        return cartRepository.findCartsWithProductByUserId(userResponse.getId());
    }

    public void deleteCartByUserAndProductId(UserResponse userResponse, Long productId) {
        cartDao.deleteByUserIdAndProductId(userResponse.getId(), productId);
    }
}

package cart.service;

import cart.controller.dto.response.CartItemResponse;
import cart.database.dao.CartDao;
import cart.database.dao.ProductDao;
import cart.database.repository.CartRepository;
import cart.entity.UserEntity;
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
    public void addCart(UserEntity userEntity, Long productId) {
        if (!productDao.existById(productId)) {
            throw new IllegalStateException("제품 아이디가 없습니다.");
        }

        cartDao.create(userEntity.getId(), productId, DEFAULT_COUNT);
    }

    public List<CartItemResponse> findCartItemsByUser(UserEntity userEntity) {
        return cartRepository.findCartsWithProductByUserId(userEntity.getId());
    }

    public void deleteCartByUserAndProductId(UserEntity userEntity, Long cartId) {
        cartDao.deleteByUserIdAndCartId(userEntity.getId(), cartId);
    }
}

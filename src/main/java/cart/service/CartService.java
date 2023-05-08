package cart.service;

import cart.dao.CartDao;
import cart.domain.cart.CartItem;
import cart.domain.product.Product;
import cart.entity.CartEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {
    public static final String CART_INVALID_USER_ID_ERROR_MESSAGE = "장바구니에 상품을 추가할 수 없습니다: 잘못된 사용자 ID";
    public static final String CART_INVALID_PRODUCT_ID_ERROR_MESSAGE = "장바구니에 상품을 추가할 수 없습니다: 잘못된 상품 ID";

    private final CartDao cartDao;
    private final ProductService productService;
    private final UserService userService;

    public CartService(final ProductService productService, final UserService userService, final CartDao cartDao) {
        this.productService = productService;
        this.userService = userService;
        this.cartDao = cartDao;
    }

    @Transactional
    public Long save(final Long userId, final Long productId) {
        if (!userService.isUserIdExist(userId)) {
            throw new IllegalArgumentException(CART_INVALID_USER_ID_ERROR_MESSAGE);
        }
        if (!productService.isProductIdExist(productId)) {
            throw new IllegalArgumentException(CART_INVALID_PRODUCT_ID_ERROR_MESSAGE);
        }

        return cartDao.insert(userId, productId);
    }

    @Transactional(readOnly = true)
    public List<CartItem> findByUserId(final Long userId) {
        final List<CartEntity> cartEntities = cartDao.findByUserId(userId);
        return cartEntities.stream().map(this::generateCartItem).collect(Collectors.toUnmodifiableList());
    }

    private CartItem generateCartItem(final CartEntity cartEntity) {
        final Product product = productService.findById(cartEntity.productId);
        return new CartItem(cartEntity.id, product);
    }

    @Transactional
    public void delete(final Long id) {
        cartDao.deleteById(id);
    }
}

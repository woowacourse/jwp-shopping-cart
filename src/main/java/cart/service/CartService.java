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
        userService.checkExistUserId(userId);
        productService.checkExistProductId(productId);

        return cartDao.insert(userId, productId);
    }

    @Transactional(readOnly = true)
    public List<CartItem> findByUserId(final Long userId) {
        final List<CartEntity> cartEntities = cartDao.findByUserId(userId);
        return cartEntities.stream().map(this::generateCartItem).collect(Collectors.toUnmodifiableList());
    }

    private CartItem generateCartItem(final CartEntity cartEntity) {
        final Product product = productService.findById(cartEntity.productId);
        return new CartItem(cartEntity.id, product.getName(), product.getPrice(), product.getImage());
    }

    @Transactional
    public void delete(final Long id) {
        cartDao.deleteById(id);
    }
}

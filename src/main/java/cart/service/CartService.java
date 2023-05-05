package cart.service;

import cart.dao.CartDao;
import cart.domain.cart.CartItem;
import cart.domain.product.Product;
import cart.entity.CartEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartDao cartDao;
    @Autowired
    private ProductService productService;

    public CartService(final CartDao cartDao) {
        this.cartDao = cartDao;
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
}

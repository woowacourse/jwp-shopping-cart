package woowacourse.shoppingcart.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Carts;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.infra.CartRepository;

@Transactional(readOnly = true)
@Service
public class SpringCartService implements CartService {
    private static final int DEFAULT_QUANTITY = 1;
    private final CartRepository cartRepository;
    private final ProductService productService;

    public SpringCartService(final CartRepository cartRepository, final ProductService productService) {
        this.cartRepository = cartRepository;
        this.productService = productService;
    }

    @Transactional
    @Override
    public void add(final long customerId, final long productId) {
        final Product product = productService.findById(productId);
        final Cart cart = new Cart(null, customerId, product, DEFAULT_QUANTITY);

        final Carts carts = cartRepository.findCartsByCustomerId(customerId);
        carts.addCart(cart);
        cartRepository.saveCarts(carts);
    }

    @Override
    public Carts findCartsByCustomerId(final long id) {
        return cartRepository.findCartsByCustomerId(id);
    }

    @Override
    public void updateQuantity(final long customerId, final long productId, final int quantity) {
        final Carts carts = cartRepository.findCartsByCustomerId(customerId);
        carts.updateQuantity(productId, quantity);

        cartRepository.saveCarts(carts);
    }

    @Override
    public void deleteByCartIds(final long customerId, final List<Long> cartIds) {
        final Carts carts = cartRepository.findCartsByCustomerId(customerId);
        final List<Cart> cartsByIds = carts.getCartsByIds(cartIds);
        carts.emptyCarts(cartsByIds);

        cartRepository.saveCarts(carts);
    }
}

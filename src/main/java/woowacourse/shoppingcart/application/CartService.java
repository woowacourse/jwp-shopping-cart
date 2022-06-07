package woowacourse.shoppingcart.application;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.domain.user.Customer;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.repository.CartItemRepository;

@Service
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductService productService;

    public CartService(CartItemRepository cartItemRepository, ProductService productService) {
        this.cartItemRepository = cartItemRepository;
        this.productService = productService;
    }

    public Cart findCart(Customer customer) {
        return cartItemRepository.findAllByCustomer(customer);
    }

    @Transactional
    public boolean registerNewCartItem(Customer customer, Long productId) {
        boolean hasRegistered = false;
        Product product = productService.findProduct(productId);
        if (!isRegisteredProduct(customer, product)) {
            cartItemRepository.save(customer, new CartItem(product));
            hasRegistered = true;
        }
        return hasRegistered;
    }

    @Transactional
    public void updateCartItem(Customer customer, Long productId, int quantity) {
        Product product = productService.findProduct(productId);
        CartItem cartItem = new CartItem(product, quantity);
        if (!isRegisteredProduct(customer, product)) {
            cartItemRepository.save(customer, cartItem);
            return;
        }
        cartItemRepository.updateQuantity(customer, cartItem);
    }

    @Transactional
    public void clearCart(Customer customer) {
        cartItemRepository.deleteByCustomer(customer);
    }

    @Transactional
    public void removeCartItems(Customer customer, List<Long> productIds) {
        cartItemRepository.deleteByCustomerAndProductIds(customer, productIds);
    }

    private boolean isRegisteredProduct(Customer customer, Product product) {
        Cart cart = findCart(customer);
        return cart.hasProduct(product);
    }
}

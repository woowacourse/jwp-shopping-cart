package cart.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cart.dao.UserDao;
import cart.domain.Cart;
import cart.domain.CartItem;
import cart.domain.Product;
import cart.domain.User;
import cart.repository.CartItemRepository;
import cart.repository.CartRepository;
import cart.repository.ProductRepository;

@Service
public class CartService {

    private final UserDao userDao;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    public CartService(UserDao userDao, CartRepository cartRepository, ProductRepository productRepository,
            CartItemRepository cartItemRepository) {
        this.userDao = userDao;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Transactional
    public void addToCart(Integer userId, Integer productId) {
        User user = userDao.selectBy(userId);
        Product product = productRepository.findBy(productId);

        Cart cart = cartRepository.getCartOf(user);
        cart.add(new CartItem(product));

        cartRepository.save(user, cart);
    }

    @Transactional
    public void deleteFromCart(Integer userId, Integer cartItemId) {
        User user = userDao.selectBy(userId);
        CartItem cartItem = cartItemRepository.findBy(cartItemId);

        Cart cart = cartRepository.getCartOf(user);
        cart.delete(cartItem);

        cartRepository.save(user, cart);
    }

    public List<CartItem> getCartItemsOf(Integer userId) {
        User user = userDao.selectBy(userId);
        Cart cart = cartRepository.getCartOf(user);
        return cart.getItems();
    }
}

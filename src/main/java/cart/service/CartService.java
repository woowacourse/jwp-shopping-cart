package cart.service;

import cart.domain.cart.Cart;
import cart.domain.cart.CartRepository;
import cart.domain.product.Product;
import cart.domain.user.User;
import cart.service.dto.UserAuthDto;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CartService {

    private final UserService userService;
    private final ProductService productService;
    private final CartRepository cartRepository;

    public CartService(UserService userService, ProductService productService, CartRepository cartRepository) {
        this.userService = userService;
        this.productService = productService;
        this.cartRepository = cartRepository;
    }

    public List<Product> findProductsInCartByUser(UserAuthDto userAuthDto) {
        User signedUpUser = this.userService.signUp(userAuthDto);
        return this.cartRepository.findByUserId(signedUpUser.getId()).getProducts();
    }

    public void addProductToCartById(UserAuthDto userAuthDto, Long productId) {
        User signedUpUser = this.userService.signUp(userAuthDto);
        Product product = this.productService.findById(productId);
        Cart cart = this.cartRepository.findByUserId(signedUpUser.getId());
        cart.add(product);
        this.cartRepository.update(cart);
    }

    public void deleteProductFromCartById(UserAuthDto userAuthDto, Long productId) {
        User signedUpUser = this.userService.signUp(userAuthDto);
        Product product = this.productService.findById(productId);
        Cart cart = this.cartRepository.findByUserId(signedUpUser.getId());
        cart.delete(product);
        this.cartRepository.update(cart);
    }
}

package cart.domain.cart;

import cart.domain.cart.dto.CartAdditionProductDto;
import cart.domain.cart.dto.CartDto;
import cart.domain.product.Product;
import cart.domain.product.ProductRepository;
import cart.domain.user.User;
import cart.domain.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public List<CartDto> getCartOfUser(Long userId) {
        List<Cart> allCarts = cartRepository.findAllByUserId(userId);

        return allCarts.stream()
                .map(CartDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long addProduct(CartAdditionProductDto cartAdditionProductDto) {
        Long productId = cartAdditionProductDto.getProductId();
        Long userId = cartAdditionProductDto.getUserId();

        User user = userRepository.findById(userId);
        Product product = productRepository.findById(productId);

        return cartRepository.save(new Cart(user, product));
    }

    @Transactional
    public void deleteProduct(Long cartId) {
        cartRepository.deleteById(cartId);
    }
}

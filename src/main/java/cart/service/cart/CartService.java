package cart.service.cart;

import cart.domain.cart.Cart;
import cart.domain.product.Product;
import cart.service.cart.dto.CartAdditionProductDto;
import cart.service.cart.dto.CartDto;
import cart.service.product.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
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

        Product product = productRepository.findById(productId);

        return cartRepository.save(new Cart(product), userId);
    }

    @Transactional
    public void deleteProduct(Long cartId) {
        cartRepository.deleteById(cartId);
    }
}

package cart.domain.cart.service;

import cart.domain.cart.CartRepository;
import cart.domain.cart.service.dto.AuthorizedCartUserDto;
import cart.domain.product.Product;
import cart.domain.product.ProductRepository;
import cart.domain.user.CartUser;
import cart.domain.user.CartUserRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartUserRepository cartUserRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, CartUserRepository cartUserRepository,
                       ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartUserRepository = cartUserRepository;
        this.productRepository = productRepository;
    }

    public void addProductInCart(AuthorizedCartUserDto request, Long productId) {
        CartUser cartUser = cartUserRepository.findByEmail(request.getEmail());
        Product product = productRepository.findById(productId);

        cartRepository.addProductInCart(cartUser, product);
    }
}

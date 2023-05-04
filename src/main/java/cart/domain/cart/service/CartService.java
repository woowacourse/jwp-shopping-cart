package cart.domain.cart.service;

import cart.domain.cart.Cart;
import cart.domain.cart.CartRepository;
import cart.domain.cart.service.dto.AuthorizedCartUserDto;
import cart.domain.product.Product;
import cart.domain.product.ProductRepository;
import cart.domain.product.service.dto.ProductDto;
import cart.domain.user.CartUser;
import cart.domain.user.CartUserRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartUserRepository cartUserRepository;
    private final ProductRepository productRepository;

    public CartService(final CartRepository cartRepository, final CartUserRepository cartUserRepository,
                       final ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartUserRepository = cartUserRepository;
        this.productRepository = productRepository;
    }

    public void addProductInCart(final AuthorizedCartUserDto userDto, final Long productId) {
        final CartUser cartUser = cartUserRepository.findByEmail(userDto.getEmail());
        final Product product = productRepository.findById(productId);

        cartRepository.addProductInCart(cartUser, product);
    }

    public List<ProductDto> findAllProductsInCart(final AuthorizedCartUserDto userDto) {
        final CartUser cartUser = cartUserRepository.findByEmail(userDto.getEmail());
        final Cart cartByCartUser = cartRepository.findCartByCartUser(cartUser);

        return cartByCartUser.getProducts()
                .stream()
                .map(ProductDto::from)
                .collect(Collectors.toList());
    }

    public void deleteProductInCart(final AuthorizedCartUserDto userDto, final Long productId) {
        final CartUser cartUser = cartUserRepository.findByEmail(userDto.getEmail());
        final Product product = productRepository.findById(productId);

        cartRepository.deleteProductInCart(cartUser, product);
    }
}

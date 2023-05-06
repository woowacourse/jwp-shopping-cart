package cart.domain.cart.service;

import cart.domain.cart.CartRepository;
import cart.domain.cart.service.dto.AuthorizedCartUserDto;
import cart.domain.cart.usecase.DeleteOneProductInCartUseCase;
import cart.domain.product.Product;
import cart.domain.product.ProductRepository;
import cart.domain.user.CartUser;
import cart.domain.user.CartUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class DeleteOneProductInCartService implements DeleteOneProductInCartUseCase {

    private final CartRepository cartRepository;
    private final CartUserRepository cartUserRepository;
    private final ProductRepository productRepository;

    public DeleteOneProductInCartService(final CartRepository cartRepository,
                                         final CartUserRepository cartUserRepository,
                                         final ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartUserRepository = cartUserRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void deleteSingleProductInCart(final AuthorizedCartUserDto userDto, final Long productId) {
        final CartUser cartUser = cartUserRepository.findByEmail(userDto.getEmail());
        final Product product = productRepository.findById(productId);

        cartRepository.deleteProductInCart(cartUser, product);
    }
}

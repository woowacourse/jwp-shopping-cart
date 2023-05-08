package cart.cart.service;

import cart.cart.domain.CartRepository;
import cart.cart.service.dto.AuthorizedCartUserDto;
import cart.cart.usecase.DeleteOneProductInCartUseCase;
import cart.exception.NoSuchDataExistException;
import cart.product.domain.Product;
import cart.product.domain.ProductRepository;
import cart.user.domain.CartUser;
import cart.user.domain.CartUserRepository;
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
        final CartUser cartUser = cartUserRepository.findByEmail(userDto.getEmail())
                .orElseThrow(NoSuchDataExistException::new);
        final Product product = productRepository.findById(productId)
                .orElseThrow(NoSuchDataExistException::new);

        cartRepository.deleteProductInCart(cartUser, product);
    }
}

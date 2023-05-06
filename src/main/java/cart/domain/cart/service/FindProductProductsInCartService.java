package cart.domain.cart.service;

import cart.domain.cart.Cart;
import cart.domain.cart.CartRepository;
import cart.domain.cart.service.dto.AuthorizedCartUserDto;
import cart.domain.cart.usecase.FindAllProductsInCartUseCase;
import cart.domain.product.service.dto.ProductResponseDto;
import cart.domain.user.CartUser;
import cart.domain.user.CartUserRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class FindProductProductsInCartService implements FindAllProductsInCartUseCase {

    private final CartRepository cartRepository;
    private final CartUserRepository cartUserRepository;

    public FindProductProductsInCartService(final CartRepository cartRepository,
                                            final CartUserRepository cartUserRepository) {
        this.cartRepository = cartRepository;
        this.cartUserRepository = cartUserRepository;
    }

    @Override
    public List<ProductResponseDto> findAllProductsInCart(final AuthorizedCartUserDto userDto) {
        final CartUser cartUser = cartUserRepository.findByEmail(userDto.getEmail());
        final Cart cartByCartUser = cartRepository.findCartByCartUser(cartUser);

        return cartByCartUser.getProducts()
                .stream()
                .map(ProductResponseDto::from)
                .collect(Collectors.toList());
    }
}

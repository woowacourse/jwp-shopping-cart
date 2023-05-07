package cart.cart.service;

import cart.cart.domain.Cart;
import cart.cart.domain.CartRepository;
import cart.cart.service.dto.AuthorizedCartUserDto;
import cart.cart.usecase.FindAllProductsInCartUseCase;
import cart.exception.NoSuchDataExistException;
import cart.product.service.dto.ProductResponseDto;
import cart.user.domain.CartUser;
import cart.user.domain.CartUserRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class FindAllProductsInCartService implements FindAllProductsInCartUseCase {

    private final CartRepository cartRepository;
    private final CartUserRepository cartUserRepository;

    public FindAllProductsInCartService(final CartRepository cartRepository,
                                        final CartUserRepository cartUserRepository) {
        this.cartRepository = cartRepository;
        this.cartUserRepository = cartUserRepository;
    }

    @Override
    public List<ProductResponseDto> findAllProductsInCart(final AuthorizedCartUserDto userDto) {
        final CartUser cartUser = cartUserRepository.findByEmail(userDto.getEmail())
                .orElseThrow(NoSuchDataExistException::new);
        final Cart cartByCartUser = cartRepository.findCartByCartUser(cartUser);

        return cartByCartUser.getProducts()
                .stream()
                .map(ProductResponseDto::from)
                .collect(Collectors.toList());
    }
}

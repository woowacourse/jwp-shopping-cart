package cart.domain.cart.usecase;

import cart.domain.cart.service.dto.AuthorizedCartUserDto;

public interface DeleteOneProductInCartUseCase {
    void deleteSingleProductInCart(AuthorizedCartUserDto userDto, Long productId);
}

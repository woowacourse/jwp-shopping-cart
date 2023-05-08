package cart.cart.usecase;

import cart.cart.service.dto.AuthorizedCartUserDto;

public interface DeleteOneProductInCartUseCase {
    void deleteSingleProductInCart(AuthorizedCartUserDto userDto, Long productId);
}

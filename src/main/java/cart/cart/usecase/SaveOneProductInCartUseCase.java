package cart.cart.usecase;

import cart.cart.service.dto.AuthorizedCartUserDto;

public interface SaveOneProductInCartUseCase {
    void addSingleProductInCart(AuthorizedCartUserDto userDto, Long productId);
}

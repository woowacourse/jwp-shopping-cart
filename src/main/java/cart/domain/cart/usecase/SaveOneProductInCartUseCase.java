package cart.domain.cart.usecase;

import cart.domain.cart.service.dto.AuthorizedCartUserDto;

public interface SaveOneProductInCartUseCase {
    void addSingleProductInCart(AuthorizedCartUserDto userDto, Long productId);
}

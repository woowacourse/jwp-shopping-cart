package cart.cart.usecase;

import cart.cart.service.dto.AuthorizedCartUserDto;
import cart.product.service.dto.ProductResponseDto;
import java.util.List;

public interface FindAllProductsInCartUseCase {
    List<ProductResponseDto> findAllProductsInCart(final AuthorizedCartUserDto userDto);
}

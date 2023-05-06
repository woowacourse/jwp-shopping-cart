package cart.domain.cart.usecase;

import cart.domain.cart.service.dto.AuthorizedCartUserDto;
import cart.domain.product.service.dto.ProductResponseDto;
import java.util.List;

public interface FindAllProductsInCartUseCase {
    List<ProductResponseDto> findAllProductsInCart(final AuthorizedCartUserDto userDto);
}

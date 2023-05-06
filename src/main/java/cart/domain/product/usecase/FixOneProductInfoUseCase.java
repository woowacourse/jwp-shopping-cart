package cart.domain.product.usecase;

import cart.domain.product.service.dto.ProductModificationDto;
import cart.domain.product.service.dto.ProductResponseDto;

public interface FixOneProductInfoUseCase {
    ProductResponseDto fixSingleProductInfo(final ProductModificationDto productDto);
}

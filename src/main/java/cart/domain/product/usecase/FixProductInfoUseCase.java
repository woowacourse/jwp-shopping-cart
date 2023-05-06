package cart.domain.product.usecase;

import cart.domain.product.service.dto.ProductModificationDto;
import cart.domain.product.service.dto.ProductResponseDto;

public interface FixProductInfoUseCase {
    ProductResponseDto fixProductInfo(final ProductModificationDto productDto);
}

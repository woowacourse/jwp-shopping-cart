package cart.product.usecase;

import cart.product.service.dto.ProductModificationDto;
import cart.product.service.dto.ProductResponseDto;

public interface FixOneProductInfoUseCase {
    ProductResponseDto fixSingleProductInfo(final ProductModificationDto productDto);
}

package cart.domain.product.usecase;

import cart.domain.product.service.dto.ProductResponseDto;
import java.util.List;

public interface FindAllProductsUseCase {
    List<ProductResponseDto> getAllProducts();
}

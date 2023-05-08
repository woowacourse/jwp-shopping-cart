package cart.product.usecase;

import cart.product.service.dto.ProductResponseDto;
import java.util.List;

public interface FindAllProductsUseCase {
    List<ProductResponseDto> getAllProducts();
}

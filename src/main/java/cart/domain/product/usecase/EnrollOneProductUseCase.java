package cart.domain.product.usecase;

import cart.domain.product.service.dto.ProductCreationDto;

public interface EnrollOneProductUseCase {
    Long enroll(final ProductCreationDto productDto);
}

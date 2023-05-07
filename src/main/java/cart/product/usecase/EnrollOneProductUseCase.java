package cart.product.usecase;

import cart.product.service.dto.ProductCreationDto;

public interface EnrollOneProductUseCase {
    Long enroll(final ProductCreationDto productDto);
}

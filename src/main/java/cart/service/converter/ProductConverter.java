package cart.service.converter;

import cart.dto.request.ProductRequestDto;
import cart.entity.ProductEntity;

public class ProductConverter {

    public static ProductEntity requestDtoToEntity(final ProductRequestDto productRequestDto) {
        return new ProductEntity(productRequestDto.getName(),
                productRequestDto.getImage(),
                productRequestDto.getPrice());
    }
}

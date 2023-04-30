package cart.mapper;

import cart.dto.ProductDto;
import cart.entity.ProductEntity;

public class ProductEntityMapper {

    private ProductEntityMapper() {
    }

    public static ProductEntity from(final ProductDto productDto) {
        return ProductEntity.of(productDto.getId(), productDto.getName(), productDto.getImageUrl(), productDto.getPrice());
    }
}

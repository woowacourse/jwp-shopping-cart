package cart.service.converter;

import cart.dto.ProductRequestDto;
import cart.dto.ProductResponseDto;
import cart.entity.ProductEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ProductConverter {

    public static ProductEntity requestDtoToEntity(final ProductRequestDto productRequestDto) {
        return new ProductEntity(productRequestDto.getName(),
                productRequestDto.getImage(),
                productRequestDto.getPrice());
    }

    public static ProductResponseDto entityToResponseDto(final ProductEntity entity) {
        return new ProductResponseDto(entity.getId(),
                entity.getName(),
                entity.getImage(),
                entity.getPrice());
    }

    public static List<ProductResponseDto> entitiesToResponses(final List<ProductEntity> entities) {
        return entities.stream()
                .map(ProductConverter::entityToResponseDto)
                .collect(Collectors.toUnmodifiableList());
    }
}

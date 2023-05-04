package cart.service.converter;

import cart.dto.request.ProductRequestDto;
import cart.dto.response.ProductResponseDto;
import cart.entity.ProductEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ProductConverter {

    public static ProductEntity requestDtoToEntity(final ProductRequestDto productRequestDto) {
        return new ProductEntity(productRequestDto.getName(),
                productRequestDto.getImage(),
                productRequestDto.getPrice());
    }

    public static List<ProductResponseDto> entitiesToResponseDtos(final List<ProductEntity> entities) {
        return entities.stream()
                .map(entity -> new ProductResponseDto(
                        entity.getId(), entity.getName(), entity.getImage(), entity.getPrice()))
                .collect(Collectors.toUnmodifiableList());
    }
}

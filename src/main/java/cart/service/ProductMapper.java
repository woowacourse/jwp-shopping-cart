package cart.service;

import cart.dto.InsertRequestDto;
import cart.dto.ProductResponseDto;
import cart.dto.UpdateRequestDto;
import cart.entity.ProductEntity;

public class ProductMapper {

    public static ProductEntity toEntity(InsertRequestDto insertRequestDto) {
        return new ProductEntity(insertRequestDto.getName(), insertRequestDto.getPrice(), insertRequestDto.getImage());
    }

    public static ProductEntity toEntity(UpdateRequestDto updateRequestDto) {
        return new ProductEntity(
                updateRequestDto.getId(),
                updateRequestDto.getName(),
                updateRequestDto.getPrice(),
                updateRequestDto.getImage());
    }

    public static ProductResponseDto toDto(ProductEntity product) {
        return new ProductResponseDto(product.getId(), product.getImage(), product.getName(), product.getPrice());
    }

}

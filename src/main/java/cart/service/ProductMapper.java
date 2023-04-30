package cart.service;

import cart.dto.ProductInsertRequestDto;
import cart.dto.ProductResponseDto;
import cart.dto.ProductUpdateRequestDto;
import cart.entity.ProductEntity;

public class ProductMapper {

    public static ProductEntity toEntity(ProductInsertRequestDto productInsertRequestDto) {
        return new ProductEntity(productInsertRequestDto.getName(), productInsertRequestDto.getPrice(), productInsertRequestDto.getImage());
    }

    public static ProductEntity toEntity(ProductUpdateRequestDto productUpdateRequestDto) {
        return new ProductEntity(
                productUpdateRequestDto.getId(),
                productUpdateRequestDto.getName(),
                productUpdateRequestDto.getPrice(),
                productUpdateRequestDto.getImage());
    }

    public static ProductResponseDto toDto(ProductEntity product) {
        return new ProductResponseDto(product.getId(), product.getImage(), product.getName(), product.getPrice());
    }

}

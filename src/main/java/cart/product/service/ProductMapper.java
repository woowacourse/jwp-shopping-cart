package cart.product.service;

import cart.product.dto.ProductInsertRequestDto;
import cart.product.dto.ProductUpdateRequestDto;
import cart.product.entity.ProductEntity;

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

}

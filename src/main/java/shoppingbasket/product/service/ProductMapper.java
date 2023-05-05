package shoppingbasket.product.service;

import shoppingbasket.product.dto.ProductInsertRequestDto;
import shoppingbasket.product.dto.ProductUpdateRequestDto;
import shoppingbasket.product.entity.ProductEntity;

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

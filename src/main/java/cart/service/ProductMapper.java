package cart.service;

import cart.domain.Product;
import cart.dto.ProductRequestDto;
import cart.dto.ProductResponseDto;
import cart.dto.entity.ProductEntity;

public class ProductMapper {
    private ProductMapper() {
    }

    public static Product requestDtoToProduct(ProductRequestDto requestDto) {
        return new Product(requestDto.getId(), requestDto.getName(), requestDto.getImage(), requestDto.getPrice());
    }

    public static ProductEntity productToEntity(Product product) {
        return new ProductEntity(product.getId(), product.getName(), product.getImage(), product.getPrice());
    }

    public static ProductResponseDto entityToResponseDto(ProductEntity entity) {
        return new ProductResponseDto(entity.getId(), entity.getName(), entity.getImage(), entity.getPrice());
    }
}

package cart.mapper;

import cart.dto.ProductCreationRequest;
import cart.dto.ProductDto;
import cart.dto.ProductModificationRequest;
import cart.entity.ProductEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ProductDtoMapper {

    private ProductDtoMapper() {
    }

    public static ProductDto from(final ProductCreationRequest request) {
        return ProductDto.of(request.getName(), request.getImage(), request.getPrice());
    }

    public static ProductDto from(final ProductModificationRequest request) {
        return ProductDto.of(request.getId(), request.getName(), request.getImage(), request.getPrice());
    }

    public static ProductDto from(final ProductEntity productEntity) {
        return ProductDto.of(productEntity.getId(), productEntity.getName(), productEntity.getImageUrl(), productEntity.getPrice());
    }

    public static List<ProductDto> from(final List<ProductEntity> productEntities) {
        return productEntities.stream()
                .map(ProductDtoMapper::from)
                .collect(Collectors.toList());
    }
}

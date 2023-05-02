package cart.mapper;

import cart.dto.ProductResponse;
import cart.domain.product.ProductEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ProductResponseMapper {

    private ProductResponseMapper() {
    }

    public static ProductResponse from(final ProductEntity productEntity) {
        return ProductResponse.of(productEntity.getId(), productEntity.getName(), productEntity.getImageUrl(), productEntity.getPrice());
    }

    public static List<ProductResponse> from(final List<ProductEntity> productEntities) {
        return productEntities.stream()
                .map(ProductResponseMapper::from)
                .collect(Collectors.toList());
    }
}

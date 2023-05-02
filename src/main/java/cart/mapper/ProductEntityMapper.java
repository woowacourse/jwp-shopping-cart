package cart.mapper;

import cart.dto.ProductCreationRequest;
import cart.dto.ProductModificationRequest;
import cart.domain.product.ProductEntity;

public class ProductEntityMapper {

    private ProductEntityMapper() {
    }

    public static ProductEntity from(final ProductCreationRequest request) {
        return ProductEntity.of(request.getName(), request.getImage(), request.getPrice());
    }

    public static ProductEntity from(final ProductModificationRequest request) {
        return ProductEntity.of(request.getId(), request.getName(), request.getImage(), request.getPrice());
    }
}

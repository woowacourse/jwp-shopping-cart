package cart.mapper;

import cart.dto.ProductCreationRequest;
import cart.dto.ProductModificationRequest;
import cart.domain.product.Product;

public class ProductMapper {

    private ProductMapper() {
    }

    public static Product from(final ProductCreationRequest request) {
        return Product.of(request.getName(), request.getImage(), request.getPrice());
    }

    public static Product from(final ProductModificationRequest request) {
        return Product.of(request.getId(), request.getName(), request.getImage(), request.getPrice());
    }
}

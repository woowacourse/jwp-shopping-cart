package cart.mapper;

import cart.dto.ProductResponse;
import cart.domain.product.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductResponseMapper {

    private ProductResponseMapper() {
    }

    public static ProductResponse from(final Product product) {
        return ProductResponse.of(product.getId(), product.getName(), product.getImageUrl(), product.getPrice());
    }

    public static List<ProductResponse> from(final List<Product> productEntities) {
        return productEntities.stream()
                .map(ProductResponseMapper::from)
                .collect(Collectors.toList());
    }
}

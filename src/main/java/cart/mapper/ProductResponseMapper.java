package cart.mapper;

import cart.domain.product.Product;
import cart.dto.ProductResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductResponseMapper {

    public static ProductResponse from(final Product product) {
        return ProductResponse.of(product.getId(), product.getName(), product.getImageUrl(), product.getPrice());
    }

    public static List<ProductResponse> from(final List<Product> productEntities) {
        return productEntities.stream()
                .map(ProductResponseMapper::from)
                .collect(Collectors.toList());
    }
}

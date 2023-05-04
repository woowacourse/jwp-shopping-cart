package cart.mapper;

import cart.domain.product.Product;
import cart.dto.ProductCreationRequest;
import cart.dto.ProductModificationRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductMapper {

    public static Product from(final ProductCreationRequest request) {
        return Product.of(request.getName(), request.getImageUrl(), request.getPrice());
    }

    public static Product from(final ProductModificationRequest request) {
        return Product.of(request.getId(), request.getName(), request.getImageUrl(), request.getPrice());
    }
}

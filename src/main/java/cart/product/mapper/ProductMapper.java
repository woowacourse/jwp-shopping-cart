package cart.product.mapper;

import cart.product.domain.Product;
import cart.product.dto.ProductCreationRequest;
import cart.product.dto.ProductModificationRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductMapper {

    public static Product from(final ProductCreationRequest request) {
        return Product.of(request.getName(), request.getImage(), request.getPrice());
    }

    public static Product from(final ProductModificationRequest request) {
        return Product.of(request.getId(), request.getName(), request.getImage(), request.getPrice());
    }
}

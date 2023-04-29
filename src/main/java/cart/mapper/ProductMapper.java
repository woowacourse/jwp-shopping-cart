package cart.mapper;

import cart.domain.Product;
import cart.dto.ProductRequest;
import cart.dto.ProductResponse;
import cart.entity.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product requestToProduct(ProductRequest request) {
        return new Product(request.getName(), request.getImage(), request.getPrice());
    }

    public ProductResponse entityToResponse(ProductEntity entity) {
        return new ProductResponse(entity.getId(), entity.getName(), entity.getImage(), entity.getPrice());
    }
}

package cart.service;

import cart.dao.entity.Product;
import cart.dto.ProductSaveRequest;
import cart.dto.ProductUpdateRequest;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    ProductMapper() {
    }

    public Product mapFrom(final ProductSaveRequest productSaveRequest) {
        return new Product(
                productSaveRequest.getName(),
                productSaveRequest.getPrice(),
                productSaveRequest.getImgUrl()
        );
    }

    public Product mapFrom(final ProductUpdateRequest request) {
        return new Product(
                request.getName(),
                request.getPrice(),
                request.getImgUrl()
        );
    }
}

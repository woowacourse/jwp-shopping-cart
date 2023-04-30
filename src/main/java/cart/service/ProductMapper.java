package cart.service;

import cart.dao.entity.Product;
import cart.dto.ProductResponse;
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

    public ProductResponse mapToProductResponse(final Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImgUrl());
    }

    public Product mapFrom(final ProductUpdateRequest request) {
        return new Product(
                request.getName(),
                request.getPrice(),
                request.getImgUrl()
        );
    }
}

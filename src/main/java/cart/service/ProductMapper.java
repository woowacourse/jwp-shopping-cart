package cart.service;

import cart.dao.entity.Product;
import cart.dto.ProductRequest;
import cart.dto.ProductResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    ProductMapper() {
    }

    public Product mapFrom(final ProductRequest productRequest) {
        return new Product(
                productRequest.getName(),
                productRequest.getPrice(),
                productRequest.getImgUrl()
        );
    }

    public ProductResponse mapToProductResponse(final Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImgUrl());
    }
}

package cart.dto;

import cart.dao.entity.Product;

public class ProductMapper {
    public ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImgUrl()
        );
    }

    public Product toProduct(ProductUpdateRequest productUpdateRequest) {
        return new Product(
                productUpdateRequest.getName(),
                productUpdateRequest.getPrice(),
                productUpdateRequest.getImgUrl()
        );
    }

    public Product toProduct(ProductCreateRequest productCreateRequest) {
        return new Product(
                productCreateRequest.getName(),
                productCreateRequest.getPrice(),
                productCreateRequest.getImgUrl()
        );
    }
}

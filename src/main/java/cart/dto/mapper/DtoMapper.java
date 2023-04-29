package cart.dto.mapper;

import cart.domain.product.Product;
import cart.dto.ProductDto;
import cart.dto.request.ProductAddRequest;
import cart.dto.request.ProductUpdateRequest;

public class DtoMapper {

    private DtoMapper() {
    }

    public static ProductDto toProductDto(Product product) {
        return new ProductDto(product.getId(), product.getName(), product.getImageUrl(), product.getPrice());
    }

    public static Product toProduct(ProductAddRequest productAddRequest) {
        return new Product(productAddRequest.getName(), productAddRequest.getImageUrl(), productAddRequest.getPrice());
    }

    public static Product toProduct(ProductUpdateRequest productUpdateRequest) {
        return new Product(productUpdateRequest.getId(), productUpdateRequest.getName(), productUpdateRequest.getImageUrl(), productUpdateRequest.getPrice());
    }
}

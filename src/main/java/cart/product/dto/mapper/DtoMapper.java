package cart.product.dto.mapper;

import cart.product.domain.Product;
import cart.product.dto.ProductDto;
import cart.product.dto.request.ProductAddRequest;
import cart.product.dto.request.ProductUpdateRequest;

public class DtoMapper {

    private DtoMapper() {
    }

    public static ProductDto toProductDto(Product product) {
        return new ProductDto(product.getId(), product.getName(), product.getImageUrl(), product.getPrice());
    }

    public static Product toValidProduct(ProductAddRequest productAddRequest) {
        return new Product(productAddRequest.getName(), productAddRequest.getImageUrl(), productAddRequest.getPrice());
    }

    public static Product toValidProduct(ProductUpdateRequest productUpdateRequest) {
        return new Product(productUpdateRequest.getId(), productUpdateRequest.getName(), productUpdateRequest.getImageUrl(), productUpdateRequest.getPrice());
    }
}

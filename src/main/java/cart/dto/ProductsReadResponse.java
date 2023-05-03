package cart.dto;

import cart.domain.Product;
import java.util.List;
import java.util.stream.Collectors;

public class ProductsReadResponse {

    private final List<ProductDto> products;

    private ProductsReadResponse(final List<ProductDto> products) {
        this.products = products;
    }

    public static ProductsReadResponse from(final List<Product> products) {
        List<ProductDto> productsResponseDto = products.stream()
                .map(ProductDto::from)
                .collect(Collectors.toList());

        return new ProductsReadResponse(productsResponseDto);
    }

    public List<ProductDto> getProducts() {
        return products;
    }
}

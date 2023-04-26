package cart.dto;

import java.util.List;

public class ProductQueryResponseDto {

    private final List<ProductDto> products;

    public ProductQueryResponseDto(final List<ProductDto> products) {
        this.products = products;
    }

    public List<ProductDto> getProducts() {
        return products;
    }
}

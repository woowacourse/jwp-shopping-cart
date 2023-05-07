package cart.dto;

import java.util.List;

public class CartProductDto {
    private final List<ProductDto> products;

    public CartProductDto(final List<ProductDto> products) {
        this.products = products;
    }

    public List<ProductDto> getProducts() {
        return products;
    }
}

package woowacourse.shoppingcart.dto;

import java.util.List;

public class ProductsDto {
    private List<ProductDto> products;

    private ProductsDto() {
    }

    public ProductsDto(List<ProductDto> products) {
        this.products = products;
    }

    public List<ProductDto> getProducts() {
        return products;
    }
}

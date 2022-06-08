package woowacourse.shoppingcart.application.dto;

import java.util.List;

public class ProductsDto {

    private int totalCount;
    private List<ProductDto> products;

    public ProductsDto() {
    }

    public ProductsDto(final int totalCount, final List<ProductDto> products) {
        this.totalCount = totalCount;
        this.products = products;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public List<ProductDto> getProducts() {
        return products;
    }
}

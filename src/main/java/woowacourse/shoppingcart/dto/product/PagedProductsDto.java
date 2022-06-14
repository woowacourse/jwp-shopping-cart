package woowacourse.shoppingcart.dto.product;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.domain.Product;

public class PagedProductsDto {

    private List<ProductDto> products;
    private int totalCount;

    public PagedProductsDto() {
    }

    public PagedProductsDto(final List<ProductDto> products, final int totalCount) {
        this.totalCount = totalCount;
        this.products = products;
    }

    public static PagedProductsDto of(final List<Product> products, final int totalSize) {
        final List<ProductDto> productDtos = products.stream()
                .map(ProductDto::from)
                .collect(Collectors.toList());
        return new PagedProductsDto(productDtos, totalSize);
    }

    public int getTotalCount() {
        return totalCount;
    }

    public List<ProductDto> getProducts() {
        return products;
    }
}

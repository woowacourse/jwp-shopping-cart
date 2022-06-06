package woowacourse.shoppingcart.dto.response;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.domain.Product;

public class ProductsDto {

    private List<ProductDto> products;

    public ProductsDto() {
    }

    public ProductsDto(List<Product> products) {
        this.products = products.stream()
                .map(ProductDto::new)
                .collect(Collectors.toList());
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDto> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductsDto that = (ProductsDto) o;
        return Objects.equals(products, that.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(products);
    }
}

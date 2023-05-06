package cart.presentation.adapter;

import cart.business.domain.product.Product;
import cart.business.domain.product.ProductId;
import cart.business.domain.product.ProductImage;
import cart.business.domain.product.ProductName;
import cart.business.domain.product.ProductPrice;
import cart.presentation.dto.ProductDto;

public class ProductConverter {

    private static final Integer NULL_ID = null;

    public static Product toEntityWithoutId(ProductDto productDto) {
        return new Product(
                new ProductId(NULL_ID),
                new ProductName(productDto.getName()),
                new ProductImage(productDto.getUrl()),
                new ProductPrice(productDto.getPrice())
        );
    }

    public static Product toEntityWithId(ProductDto productDto) {
        return new Product(
                new ProductId(productDto.getId()),
                new ProductName(productDto.getName()),
                new ProductImage(productDto.getUrl()),
                new ProductPrice(productDto.getPrice())
        );
    }

    public static ProductDto toDto(Product product) {
        return new ProductDto(product.getId(), product.getName(),
                product.getUrl(), product.getPrice());
    }
}

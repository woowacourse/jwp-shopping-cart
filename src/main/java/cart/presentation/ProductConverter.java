package cart.presentation;

import cart.business.domain.product.Product;
import cart.business.domain.product.ProductImage;
import cart.business.domain.product.ProductName;
import cart.business.domain.product.ProductPrice;
import cart.presentation.dto.ProductDto;

public class ProductConverter {

    public static Product toProductWithoutId(ProductDto productDto) {
        return new Product(
                null,
                new ProductName(productDto.getName()),
                new ProductImage(productDto.getUrl()),
                new ProductPrice(productDto.getPrice())
        );
    }

    public static Product toProductWithId(ProductDto productDto) {
        return new Product(
                productDto.getId(),
                new ProductName(productDto.getName()),
                new ProductImage(productDto.getUrl()),
                new ProductPrice(productDto.getPrice())
        );
    }

    public static ProductDto toProductDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getUrl(),
                product.getPrice()
        );
    }
}

package cart.application;

import cart.business.domain.Product;
import cart.business.domain.ProductImage;
import cart.business.domain.ProductName;
import cart.business.domain.ProductPrice;
import cart.presentation.dto.ProductDto;

public class ProductConverter {

    public static Product toProduct(ProductDto productDto) {
        return new Product(
                IdSequencer.get(),
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

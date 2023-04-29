package cart.application;

import cart.business.ProductCRUDService;
import cart.business.domain.Product;
import cart.config.annonation.Application;
import cart.presentation.dto.ProductDto;
import cart.presentation.dto.ProductIdDto;

import java.util.List;
import java.util.stream.Collectors;

@Application
public class ProductCRUDApplication {

    private final ProductCRUDService productCRUDService;

    public ProductCRUDApplication(ProductCRUDService productCRUDService) {
        this.productCRUDService = productCRUDService;
    }

    public void create(ProductDto productDto) {
        Product product = ProductConverter.toProductWithoutId(productDto);
        productCRUDService.create(product);
    }

    public List<ProductDto> readAll() {
        return productCRUDService.readAll()
                .stream()
                .map(ProductConverter::toProductDto)
                .collect(Collectors.toList());
    }

    public void update(ProductDto productDto) {
        Product product = ProductConverter.toProductWithId(productDto);
        productCRUDService.update(product);
    }

    public void delete(ProductIdDto productIdDto) {
        productCRUDService.delete(productIdDto.getId());
    }
}

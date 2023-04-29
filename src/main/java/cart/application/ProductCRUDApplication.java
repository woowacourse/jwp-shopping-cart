package cart.application;

import cart.business.CreateProductService;
import cart.business.DeleteProductService;
import cart.business.ReadProductService;
import cart.business.UpdateProductService;
import cart.business.domain.Product;
import cart.presentation.dto.ProductDto;
import cart.presentation.dto.ProductIdDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

// TODO: Custom Annotation `Application` 만들어보기
@Component
public class ProductCRUDApplication {

    private final CreateProductService createProductService;
    private final DeleteProductService deleteProductService;
    private final ReadProductService readProductService;
    private final UpdateProductService updateProductService;

    public ProductCRUDApplication(
            CreateProductService createProductService,
            DeleteProductService deleteProductService,
            ReadProductService readProductService,
            UpdateProductService updateProductService
    ) {
        this.createProductService = createProductService;
        this.deleteProductService = deleteProductService;
        this.readProductService = readProductService;
        this.updateProductService = updateProductService;
    }

    public void create(ProductDto productDto) {
        Product product = ProductConverter.toProduct(productDto);
        createProductService.perform(product);
    }

    public List<ProductDto> readAll() {
        return readProductService.perform()
                .stream()
                .map(ProductConverter::toProductDto)
                .collect(Collectors.toList());
    }

    public void update(ProductDto productDto) {
        Product product = ProductConverter.toProductWithId(productDto);
        updateProductService.perform(product);
    }

    public void delete(ProductIdDto productIdDto) {
        deleteProductService.perform(productIdDto.getId());
    }
}

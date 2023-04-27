package cart.presentation;


import cart.business.CreateProductService;
import cart.business.DeleteProductService;
import cart.business.ReadProductService;
import cart.business.UpdateProductService;
import cart.business.domain.Product;
import cart.business.domain.ProductImage;
import cart.business.domain.ProductName;
import cart.business.domain.ProductPrice;
import cart.presentation.dto.ProductDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/product")
public class ProductController {

    private final CreateProductService createProductService;
    private final ReadProductService readProductService;
    private final UpdateProductService updateProductService;
    private final DeleteProductService deleteProductService;

    public ProductController(
            CreateProductService createProductService,
            ReadProductService readProductService,
            UpdateProductService updateProductService,
            DeleteProductService deleteProductService
    ) {
        this.createProductService = createProductService;
        this.readProductService = readProductService;
        this.updateProductService = updateProductService;
        this.deleteProductService = deleteProductService;
    }

    @PostMapping(path = "/create")
    public void productCreate(@RequestBody ProductDto request) {
        Product product = makeProductFromDtoWithoutId(request);
        createProductService.perform(product);
    }

    @GetMapping(path = "/read")
    public ResponseEntity<List<ProductDto>> productRead() {
        List<Product> products = readProductService.perform();
        List<ProductDto> productsToResponse = products.stream()
                .map(product -> new ProductDto(product.getId(),
                        product.getName(),
                        product.getUrl(),
                        product.getPrice()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(productsToResponse);
    }

    @PostMapping(path = "/update")
    public void productUpdate(@RequestBody ProductDto request) {
        Product product = makeProductFromDtoWithId(request);
        updateProductService.perform(product);
    }

    @DeleteMapping(path = "/delete")
    public void productDelete(@RequestBody ProductDto request) {
        Product product = makeProductFromDtoWithId(request);
        deleteProductService.perform(product);
    }

    private Product makeProductFromDtoWithoutId(ProductDto request) {
        return new Product(
                null,
                new ProductName(request.getName()),
                new ProductImage(request.getUrl()),
                new ProductPrice(request.getPrice())
        );
    }

    private Product makeProductFromDtoWithId(ProductDto request) {
        return new Product(
                request.getId(),
                new ProductName(request.getName()),
                new ProductImage(request.getUrl()),
                new ProductPrice(request.getPrice())
        );
    }
}

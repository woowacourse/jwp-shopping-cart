package cart.controller;

import cart.dto.ProductDto;
import cart.request.ProductRequest;
import cart.service.ProductService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductResourceController {

    private final ProductService productService;

    public ProductResourceController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/admin")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createProduct(@RequestBody @Valid final ProductRequest productRequest) {
        final ProductDto productDto = new ProductDto(
                productRequest.getName(),
                productRequest.getPrice(),
                productRequest.getImageUrl()
        );

        productService.register(productDto);
    }

    @PutMapping("/admin/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateProduct(@PathVariable final long id, final @RequestBody @Valid ProductRequest productRequest) {
        productService.updateProduct(id, new ProductDto(productRequest));
    }

    @DeleteMapping("/admin/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteProduct(@PathVariable final long id) {
        productService.deleteProduct(id);
    }
}

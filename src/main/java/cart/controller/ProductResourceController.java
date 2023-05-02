package cart.controller;

import cart.domain.product.Product;
import cart.dto.application.ProductDto;
import cart.dto.request.ProductRequest;
import cart.dto.response.ProductResponse;
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

    @PostMapping("/admin/products")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ProductResponse createProduct(@RequestBody @Valid final ProductRequest productRequest) {
        final ProductDto productDto = new ProductDto(
                productRequest.getName(),
                productRequest.getPrice(),
                productRequest.getImageUrl()
        );
        final Product product = productService.register(productDto);
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getImageUrl());
    }

    @PutMapping("/admin/products/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public ProductResponse updateProduct(@PathVariable final long id,
                                         @RequestBody @Valid final ProductRequest productRequest) {
        final Product product = productService.updateProduct(id, new ProductDto(productRequest));
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getImageUrl());
    }

    @DeleteMapping("/admin/products/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable final long id) {
        productService.deleteProduct(id);
    }
}

package cart.controller;

import cart.domain.product.Product;
import cart.domain.product.ProductEntity;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/products")
public class ProductResourceController {

    private final ProductService productService;

    public ProductResourceController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ProductResponse createProduct(@RequestBody @Valid final ProductRequest productRequest) {
        final Product product = new Product(
                productRequest.getName(),
                productRequest.getPrice(),
                productRequest.getImageUrl()
        );
        final ProductEntity result = productService.register(product);
        return new ProductResponse(result.getId(), result.getName(), result.getPrice(), result.getImageUrl());
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public ProductResponse updateProduct(@PathVariable final long id,
                                         @RequestBody @Valid final ProductRequest productRequest) {
        final Product product = new Product(
                productRequest.getName(),
                productRequest.getPrice(),
                productRequest.getImageUrl()
        );
        final ProductEntity result = productService.updateProduct(id, product);
        return new ProductResponse(result.getId(), result.getName(), result.getPrice(), result.getImageUrl());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable final long id) {
        productService.deleteProduct(id);
    }
}

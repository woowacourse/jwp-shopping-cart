package cart.controller.api;

import cart.controller.dto.ProductRequest;
import cart.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ProductApiController {

    private final ProductService productService;

    public ProductApiController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public void save(
            @Valid @RequestBody final ProductRequest request
    ) {
        productService.save(request);
    }

    @PutMapping("/products/{id}")
    public void update(
            @PathVariable("id") final Long id,
            @Valid @RequestBody final ProductRequest request) {
        productService.update(id, request);
    }

    @DeleteMapping("/products/{id}")
    public void delete(@PathVariable("id") final Long id) {
        productService.delete(id);
    }
}

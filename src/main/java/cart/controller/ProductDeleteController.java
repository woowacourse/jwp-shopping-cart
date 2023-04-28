package cart.controller;

import cart.service.ProductCommandService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductDeleteController {

    private final ProductCommandService productCommandService;

    public ProductDeleteController(final ProductCommandService productCommandService) {
        this.productCommandService = productCommandService;
    }

    @DeleteMapping("/products/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable final long id) {
        productCommandService.delete(id);
    }
}

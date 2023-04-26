package cart.controller;

import cart.service.ProductDeleteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductDeleteController {

    private final ProductDeleteService productDeleteService;

    public ProductDeleteController(final ProductDeleteService productDeleteService) {
        this.productDeleteService = productDeleteService;
    }

    @DeleteMapping("/products/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable final long id) {
        productDeleteService.delete(id);
    }
}

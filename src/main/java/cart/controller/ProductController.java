package cart.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @DeleteMapping("/products/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable final long id) {
    }
}

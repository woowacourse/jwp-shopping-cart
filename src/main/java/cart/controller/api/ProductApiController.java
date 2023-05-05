package cart.controller.api;

import cart.controller.dto.ProductRequest;
import cart.dao.ProductDao;
import cart.domain.Product;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ProductApiController {

    private final ProductDao productDao;

    public ProductApiController(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public void save(
            @Valid @RequestBody final ProductRequest request
    ) {
        productDao.save(new Product(request.getName(), request.getImageUrl(), request.getPrice()));
    }

    @PutMapping("/products/{id}")
    public void update(
            @PathVariable("id") final Long id,
            @Valid @RequestBody final ProductRequest request) {
        productDao.update(id, new Product(id, request.getName(), request.getImageUrl(), request.getPrice()));
    }

    @DeleteMapping("/products/{id}")
    public void delete(@PathVariable("id") final Long id) {
        productDao.deleteById(id);
    }
}

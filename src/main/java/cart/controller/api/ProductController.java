package cart.controller.api;

import cart.controller.dto.ProductRequest;
import cart.dao.ProductDao;
import cart.domain.Product;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    private final ProductDao productDao;

    public ProductController(final ProductDao productDao) {
        this.productDao = productDao;
    }

    // TODO /admin/products  VS  /products 가 고민입니다
    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody final ProductRequest request) {
        productDao.save(new Product(request.getName(), request.getImageUrl(), request.getPrice()));
    }

    @PutMapping("/products/{id}")
    public void update(
            @PathVariable("id") final Long id,
            @RequestBody final ProductRequest request) {
        productDao.update(new Product(id, request.getName(), request.getImageUrl(), request.getPrice()));
    }

    @DeleteMapping("/products/{id}")
    public void delete(@PathVariable("id") final Long id) {
        productDao.deleteById(id);
    }
}

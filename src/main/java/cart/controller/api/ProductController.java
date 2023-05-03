package cart.controller.api;

import cart.controller.dto.ProductRequest;
import cart.dao.ProductDao;
import cart.domain.Product;
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
public class ProductController {

    private final ProductDao productDao;

    public ProductController(final ProductDao productDao) {
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
        final Product product = productDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("찾으시는 상품이 없습니다."));
        productDao.update(product.update(request.getName(), request.getImageUrl(), request.getPrice()));
    }

    @DeleteMapping("/products/{id}")
    public void delete(@PathVariable("id") final Long id) {
        productDao.deleteById(id);
    }
}

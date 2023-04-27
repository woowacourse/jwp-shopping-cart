package cart.controller;

import cart.controller.dto.ProductRequest;
import cart.dao.ProductDao;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductDao productDao;

    public ProductController(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @PostMapping
    public ResponseEntity<Void> createProduct(@Valid @RequestBody final ProductRequest productRequest) {
        productDao.save(productRequest);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Void> modifyProduct(@PathVariable final Long productId, @Valid @RequestBody final ProductRequest productRequest) {
        productDao.updateById(productId, productRequest);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> removeProduct(@PathVariable final Long productId) {
        productDao.deleteById(productId);

        return ResponseEntity.ok().build();
    }
}

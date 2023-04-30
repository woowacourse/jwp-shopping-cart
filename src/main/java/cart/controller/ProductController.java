package cart.controller;

import cart.controller.dto.ProductRequest;
import cart.dao.ProductDao;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductDao productDao;

    public ProductController(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @PostMapping
    public ResponseEntity<Void> createProduct(@Valid @RequestBody final ProductRequest productRequest) {
        Long productId = productDao.save(productRequest);

        return ResponseEntity
                .created(URI.create("/products/" + productId))
                .build();
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Integer> modifyProduct(@PathVariable final Long productId, @Valid @RequestBody final ProductRequest productRequest) {
        int updatedCount = productDao.updateById(productId, productRequest);

        return ResponseEntity
                .ok()
                .body(updatedCount);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> removeProduct(@PathVariable final Long productId) {
        productDao.deleteById(productId);

        return ResponseEntity
                .noContent()
                .build();
    }
}

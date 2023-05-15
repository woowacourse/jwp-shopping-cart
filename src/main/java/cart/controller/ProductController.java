package cart.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cart.controller.dto.ProductRequest;
import cart.dao.ProductDao;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductDao productDao;

    public ProductController(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @PostMapping
    public ResponseEntity<Void> createProduct(@Valid @RequestBody final ProductRequest productRequest) {
        productDao.save(productRequest);

        return ResponseEntity.created(URI.create("/products")).build();
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Void> modifyProduct(@PathVariable final Long productId, @Valid @RequestBody final ProductRequest productRequest) {
        productDao.updateById(productId, productRequest);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> removeProduct(@PathVariable final Long productId) {
        productDao.deleteById(productId);

        return ResponseEntity.noContent().build();
    }
}

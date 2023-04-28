package cart.controller;

import cart.controller.dto.ProductRequest;
import cart.dao.ProductDao;
import java.util.NoSuchElementException;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductDao productDao;

    public ProductController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody ProductRequest product) {
        productDao.add(product);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id,
        @Valid @RequestBody ProductRequest product) {
        final int updateCount = productDao.updateById(id, product);
        if (updateCount == 0) {
            throw new NoSuchElementException();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        final int deleteCount = productDao.deleteById(id);
        if (deleteCount == 0) {
            throw new NoSuchElementException();
        }
        return ResponseEntity.ok().build();
    }
}

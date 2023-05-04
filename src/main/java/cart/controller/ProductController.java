package cart.controller;

import cart.dao.CrudDao;
import cart.dao.ProductDao;
import java.net.URI;
import java.net.URISyntaxException;
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

    private final CrudDao productDao;

    public ProductController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody ProductRequest product)
        throws URISyntaxException {
        productDao.add(product);
        return ResponseEntity.created(new URI("/admin")).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id,
                                 @Valid @RequestBody ProductRequest product) {
        final int updateCount = productDao.updateById(id, product);
        validateChange(updateCount);
        return ResponseEntity.ok().build();
    }

    private static void validateChange(int changeColumnCount) {
        if (changeColumnCount == 0) {
            throw new NoSuchElementException();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        final int deleteCount = productDao.deleteById(id);
        validateChange(deleteCount);
        return ResponseEntity.ok().build();
    }
}

package cart.controller;

import cart.dao.ProductDao;
import cart.domain.Product;
import cart.dto.request.ProductAddRequest;
import cart.dto.request.ProductUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Slf4j
@Validated
@RestController
@RequestMapping("/product")
public class ProductApiController {

    private final ProductDao productDao;

    public ProductApiController(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody ProductAddRequest productDto) {
        productDao.insert(new Product(productDto.getName(), productDto.getImageUrl(), productDto.getPrice()));
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@Valid @RequestBody ProductUpdateRequest productDto) {
        productDao.update(new Product(productDto.getId(), productDto.getName(), productDto.getImageUrl(), productDto.getPrice()));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Positive Long id) {
        log.info("id={}", id);
        productDao.deleteById(id);
        return ResponseEntity.ok().build();
    }
}

package cart.controller;

import cart.dto.ProductDto;
import cart.service.ProductService;
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

@RequestMapping("/products")
@RestController
public class ProductApiController {

    private final ProductService productService;

    public ProductApiController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Integer> create(@Valid @RequestBody final ProductDto productDto) {
        Integer savedId = productService.insert(productDto);
        return ResponseEntity.created(URI.create("/products/" + savedId)).body(savedId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable final int id, @Valid @RequestBody final ProductDto productDto) {
        productService.update(id, productDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public Integer delete(@PathVariable final int id) {
        productService.delete(id);
        return id;
    }

}

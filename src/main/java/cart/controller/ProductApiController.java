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
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductApiController {

    private final ProductService productService;

    public ProductApiController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products/insert")
    public ResponseEntity<Integer> insert(@Valid @RequestBody ProductDto productDto) {
        Integer savedId = productService.insert(productDto);
        return ResponseEntity.created(URI.create("/products/" + savedId)).body(savedId);
    }

    @PutMapping("/products/update/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @Valid @RequestBody ProductDto productDto) {
        productService.update(id, productDto);
        return ResponseEntity.created(URI.create("/products/" + id)).build();
    }

    @DeleteMapping("/products/delete/{id}")
    public Integer delete(@PathVariable int id) {
        productService.delete(id);
        return id;
    }

}

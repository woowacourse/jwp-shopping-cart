package cart.controller;

import cart.dto.RequestCreateProductDto;
import cart.dto.RequestUpdateProductDto;
import cart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/admin")
public class ProductApiController {

    private final ProductService productService;

    @Autowired
    public ProductApiController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/product")
    public ResponseEntity<Void> createProduct(@RequestBody @Valid final RequestCreateProductDto requestCreateProductDto) {
        productService.insert(requestCreateProductDto);
        return ResponseEntity.created(URI.create("/product")).build();
    }

    @PutMapping("/product")
    public ResponseEntity<Void> updateProduct(@RequestBody @Valid final RequestUpdateProductDto requestUpdateProductDto) {
        productService.update(requestUpdateProductDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }
}

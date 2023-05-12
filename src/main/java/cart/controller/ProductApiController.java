package cart.controller;

import cart.dto.request.RequestCreateProductDto;
import cart.dto.request.RequestUpdateProductDto;
import cart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class ProductApiController {

    private final ProductService productService;

    @Autowired
    public ProductApiController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    public ResponseEntity<Void> createProduct(@RequestBody @Valid final RequestCreateProductDto requestCreateProductDto) {
        productService.addNewProduct(requestCreateProductDto);
        return ResponseEntity.created(URI.create("/products")).build();
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable final Long id, @RequestBody final RequestUpdateProductDto requestUpdateProductDto) {
        productService.update(id, requestUpdateProductDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable final Long id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }
}

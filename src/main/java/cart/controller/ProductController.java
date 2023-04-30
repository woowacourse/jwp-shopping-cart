package cart.controller;

import cart.dto.ProductRequest;
import cart.dto.ProductResponse;
import cart.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody @Valid ProductRequest productRequest) {
        Long productId = productService.create(productRequest);
        return ResponseEntity.created(URI.create("/products" + productId)).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@RequestBody ProductRequest productRequest, @PathVariable Long id) {
        ProductResponse updated = productService.update(productRequest, id);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void delete(@PathVariable Long id) {
        productService.deleteById(id);
    }
}

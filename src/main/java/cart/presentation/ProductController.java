package cart.presentation;


import cart.business.ProductService;
import cart.business.domain.Product;
import cart.presentation.dto.ProductRequest;
import cart.presentation.dto.ProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(path = "/products")
    public ResponseEntity<Integer> create(@RequestBody ProductRequest request) {
        return ResponseEntity.created(URI.create("/products")).body(productService.create(request));
    }

    @GetMapping(path = "/products")
    public ResponseEntity<List<ProductResponse>> read() {
        List<Product> products = productService.read();

        List<ProductResponse> response = products.stream()
                .map(product -> new ProductResponse(product.getId(),
                        product.getName(),
                        product.getUrl(),
                        product.getPrice()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/products/{id}")
    public ResponseEntity<Integer> update(@PathVariable Integer id, @RequestBody ProductRequest request) {
        return ResponseEntity.ok().body(productService.update(id, request));
    }

    @DeleteMapping(path = "/products/{id}")
    public ResponseEntity<Integer> delete(@PathVariable Integer id) {
        return ResponseEntity.ok().body(productService.delete(id));
    }
}

package cart.controller;

import cart.dto.request.ProductRequest;
import cart.dto.response.ProductResponse;
import cart.service.ProductService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> productList() {
        List<ProductResponse> response = productService.findAll();

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Void> productAdd(@Validated @RequestBody ProductRequest productRequest) {
        int productId = productService.save(productRequest);

        return ResponseEntity.created(URI.create("/products/" + productId)).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> productModify(@Validated @RequestBody ProductRequest productRequest,
                                              @PathVariable int id) {
        productService.update(productRequest, id);

        return ResponseEntity.created(URI.create("/products/" + id)).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> productRemove(@PathVariable int id) {
        productService.delete(id);

        return ResponseEntity.noContent().build();
    }
}

package cart.controller;

import cart.dto.request.ProductRequest;
import cart.dto.response.ProductResponse;
import cart.service.ProductService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
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

@Controller()
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public ResponseEntity<List<ProductResponse>> productList() {
        List<ProductResponse> response = productService.findAll();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping()
    public ResponseEntity<Map<String, Integer>> productAdd(
            @Validated @RequestBody ProductRequest productRequest) {
        int productId = productService.save(productRequest);

        Map<String, Integer> response = new HashMap<>();
        response.put("id", productId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> productModify(@Validated @RequestBody ProductRequest productRequest,
                                                             @PathVariable int id) {
        productService.update(productRequest, id);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> productRemove(@PathVariable int id) {
        productService.delete(id);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}

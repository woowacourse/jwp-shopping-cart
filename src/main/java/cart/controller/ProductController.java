package cart.controller;

import cart.dto.ProductRequest;
import cart.response.ApiResponse;
import cart.service.ProductService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @PostMapping()
    public ResponseEntity<ApiResponse<?>> productAdd(@Validated @RequestBody ProductRequest productRequest) {
        int productId = productService.save(productRequest);

        Map<String, Integer> result = new HashMap<>();
        result.put("id", productId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.createSuccess(result));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> productModify(@Validated @RequestBody ProductRequest productRequest,
                                                        @PathVariable int id) {
        productService.update(productRequest, id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.createSuccess(List.of()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> productRemove(@PathVariable int id) {
        productService.delete(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.createSuccess(List.of()));
    }
}

package cart.controller;

import cart.dto.ProductRequest;
import cart.service.ProductService;
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
    public ResponseEntity<Integer> productAdd(@Validated @RequestBody ProductRequest productRequest) {
        int productId = productService.save(productRequest);
        return new ResponseEntity<>(productId, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> productModify(@Validated @RequestBody ProductRequest productRequest,
                                                @PathVariable int id) {
        productService.update(productRequest, id);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> productRemove(@PathVariable int id) {
        productService.delete(id);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
}

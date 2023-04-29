package cart.controller;

import cart.dto.request.ProductRequest;
import cart.service.ProductService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//TODO: BOdy 담아주는것 고민
@RestController
@RequestMapping("/product")
public class ProductApiController {

    private final ProductService productService;

    public ProductApiController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody @Valid ProductRequest productRequest) {
        productService.save(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Void> update(@PathVariable Long productId,
                                         @RequestBody @Valid ProductRequest productRequest) {
        productService.updateById(productId, productRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> delete(@PathVariable Long productId) {
        productService.deleteById(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

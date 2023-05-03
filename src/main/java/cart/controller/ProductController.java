package cart.controller;

import cart.service.ProductService;
import cart.service.dto.ProductModifyRequest;
import cart.service.dto.ProductRegisterRequest;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Void> registerProduct(
            @Validated @RequestBody final ProductRegisterRequest productRegisterRequest) {
        Long id = productService.registerProduct(productRegisterRequest);
        return ResponseEntity.created(URI.create("/products/" + id)).build();
    }

    @PutMapping("/{product-id}")
    public ResponseEntity<Void> modifyProduct(
            @PathVariable("product-id") final Long productId,
            @Validated @RequestBody final ProductModifyRequest productModifyRequest
    ) {
        productService.modifyProduct(productId, productModifyRequest);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{product-id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("product-id") final Long productId) {
        productService.deleteProduct(productId);

        return ResponseEntity.ok().build();
    }
}

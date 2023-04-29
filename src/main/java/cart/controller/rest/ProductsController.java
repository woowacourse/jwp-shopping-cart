package cart.controller.rest;

import cart.dto.request.ProductRequest;
import cart.dto.request.ProductUpdateRequest;
import cart.dto.response.ProductResponse;
import cart.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsController {

    private final CartService cartService;

    public ProductsController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{id}")
    public ProductResponse getProduct(@PathVariable @Valid Long id) {
        return cartService.getProduct(id);
    }

    @GetMapping
    public List<ProductResponse> getProducts() {
        return cartService.getAllProducts();
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        Long createdProductId = cartService.createProduct(productRequest);

        ProductResponse createdProduct = cartService.getProduct(createdProductId);
        URI location = URI.create("/products/" + createdProductId);

        return ResponseEntity.created(location).body(createdProduct);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduct(@RequestBody @Valid ProductUpdateRequest productUpdateRequest) {
        cartService.updateProduct(productUpdateRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable @NotNull Long id) {
        cartService.deleteProduct(id);
    }
}

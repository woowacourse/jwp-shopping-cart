package cart.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cart.dto.ProductPostRequest;
import cart.dto.ProductPutRequest;
import cart.service.CartService;

@RestController
public class AdminApiController {

    private final CartService cartService;

    public AdminApiController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/admin/product")
    public ResponseEntity<Void> createProduct(@RequestBody @Valid final ProductPostRequest productPostRequest) {
        final long id = cartService.create(productPostRequest);
        return ResponseEntity.created(URI.create("/admin/product/" + id)).build();
    }

    @PutMapping("/admin/product/{id}")
    public ResponseEntity<Void> updateProduct(
        @PathVariable final Long id,
        @RequestBody @Valid final ProductPutRequest productPutRequest
    ) {
        cartService.update(id, productPutRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/admin/product/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable final Long id) {
        cartService.delete(id);
        return ResponseEntity.ok().build();
    }
}

package cart.web.admin.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cart.domain.admin.persistence.entity.ProductEntity;
import cart.domain.admin.service.CartService;
import cart.web.admin.dto.PostProductRequest;
import cart.web.admin.dto.PutProductRequest;

@RestController
public class AdminApiController {

    private final CartService cartService;

    public AdminApiController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/admin/products")
    public ResponseEntity<Void> createProduct(@RequestBody @Valid final PostProductRequest request) {
        final ProductEntity productEntity = PostProductRequest.toEntity(request);
        final long id = cartService.create(productEntity);
        return ResponseEntity.created(URI.create("/admin/products/" + id)).build();
    }

    @PutMapping("/admin/products/{id}")
    public ResponseEntity<Void> updateProduct(
        @PathVariable final Long id,
        @RequestBody @Valid final PutProductRequest putProductRequest
    ) {
        final ProductEntity productEntity = PutProductRequest.toEntity(putProductRequest);
        cartService.update(id, productEntity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/admin/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable final Long id) {
        cartService.delete(id);
        return ResponseEntity.ok().build();
    }
}

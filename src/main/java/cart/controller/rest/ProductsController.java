package cart.controller.rest;

import cart.dto.request.ProductRequest;
import cart.dto.request.ProductUpdateRequest;
import cart.dto.response.ProductResponse;
import cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;


@RestController
@RequestMapping("/products")
public class ProductsController {

    private final CartService cartService;

    public ProductsController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody @Valid ProductRequest productRequest) {
        ProductResponse productResponse = cartService.create(productRequest);

        URI createdUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productResponse.getId())
                .toUri();

        return ResponseEntity.created(createdUri).body(productResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@RequestBody @Valid ProductUpdateRequest productUpdateRequest) {
        ProductResponse productResponse = cartService.update(productUpdateRequest);
        return ResponseEntity.ok().body(productResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull Long id) {
        cartService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

package cart.controller;

import cart.dto.request.RequestCreateProductDto;
import cart.dto.request.RequestUpdateProductDto;
import cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class ProductApiController {

    private final CartService cartService;

    @Autowired
    public ProductApiController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/products")
    public ResponseEntity<Void> createProduct(@RequestBody @Valid final RequestCreateProductDto requestCreateProductDto) {
        cartService.insert(requestCreateProductDto);
        return ResponseEntity.created(URI.create("/products")).build();
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable final Long id, @RequestBody final RequestUpdateProductDto requestUpdateProductDto) {
        cartService.update(id, requestUpdateProductDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable final Long id) {
        cartService.delete(id);
        return ResponseEntity.ok().build();
    }
}

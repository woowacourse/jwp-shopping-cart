package cart.controller;

import cart.dto.RequestCreateProductDto;
import cart.dto.RequestUpdateProductDto;
import cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/admin")
public class CartApiController {

    private final CartService cartService;

    @Autowired
    public CartApiController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/product")
    public ResponseEntity<Void> createProduct(@RequestBody @Valid final RequestCreateProductDto requestCreateProductDto) {
        cartService.insert(requestCreateProductDto);
        return ResponseEntity.created(URI.create("/product")).build();
    }

    @PutMapping("/product")
    public ResponseEntity<Void> updateProduct(@RequestBody @Valid final RequestUpdateProductDto requestUpdateProductDto) {
        cartService.update(requestUpdateProductDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        cartService.delete(id);
        return ResponseEntity.ok().build();
    }
}

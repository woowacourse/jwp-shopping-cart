package cart.controller;

import cart.dto.ProductIdDto;
import cart.dto.CartDto;
import cart.dto.CartRequest;
import cart.dto.ProductDto;
import cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/carts/{memberId}")
    public ResponseEntity<List<ProductDto>> findCartProducts(@PathVariable long memberId) {
        CartDto cart = cartService.findById(memberId);
        return ResponseEntity.ok().body(cart.getProducts());
    }

    @PostMapping("/carts")
    public ResponseEntity<String> saveCart(@RequestBody CartRequest cartRequest) {
        cartService.addProducts(cartRequest);
        return ResponseEntity.created(URI.create("/carts/" + cartRequest.getMemberId())).build();
    }

    @PostMapping("/carts/{memberId}")
    public ResponseEntity<String> addProductToCart(@PathVariable long memberId, @RequestBody ProductIdDto productId) {
        cartService.addProduct(memberId, productId.getProductId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/carts/{memberId}")
    public ResponseEntity<String> removeProductFromCart(@PathVariable long memberId, @RequestParam long productId) {
        cartService.removeProduct(memberId, productId);
        return ResponseEntity.ok().build();
    }
}

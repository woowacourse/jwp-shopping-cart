package cart.controller;

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
    public ResponseEntity<List<ProductDto>> cartProductList(@PathVariable long memberId) {
        CartDto cart = cartService.findById(memberId);
        System.out.println(cart.getProducts() + "ddd");
        return ResponseEntity.ok().body(cart.getProducts());
    }

    @PostMapping("/carts")
    public ResponseEntity<String> cartSave(@RequestBody CartRequest cartRequest) {
        cartService.addProducts(cartRequest);
        return ResponseEntity.created(URI.create("/carts/" + cartRequest.getMemberId())).build();
    }

    @PostMapping("/carts/{member_id}")
    public ResponseEntity<String> cartProductAdd(@PathVariable long member_id, @RequestParam long productId) {
        cartService.addProduct(member_id, productId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/carts/{member_id}")
    public ResponseEntity<String> cartProductRemove(@PathVariable long member_id, @RequestParam long productId) {
        cartService.removeProduct(member_id, productId);
        return ResponseEntity.ok().build();
    }
}

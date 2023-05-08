package cart.controller.cart;

import cart.config.auth.Auth;
import cart.dto.CartItemDto;
import cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("")
    public ResponseEntity<List<CartItemDto>> findProduct(@Auth final String email) {
        List<CartItemDto> cartItems = cartService.findAllCartItemsByEmail(email);
        return ResponseEntity.ok().body(cartItems);
    }

    @PostMapping("")
    public ResponseEntity<Void> addProduct(@RequestBody @Valid CartItemDto cartItemDto, @Auth final String email) {
        final Long id = cartService.addProductInCart(cartItemDto.getProductId(), email);
        return ResponseEntity.created(URI.create("/carts/" + id)).build();
    }

    @DeleteMapping("/{cart_id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("cart_id") final Long cartId) {
        cartService.deleteProductByCartId(cartId);
        return ResponseEntity.noContent().build();
    }
}

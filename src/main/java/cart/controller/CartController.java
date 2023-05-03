package cart.controller;

import cart.auth.Auth;
import cart.service.CartService;
import cart.service.dto.CartRequest;
import cart.service.dto.CartResponse;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Void> addProductToCart(@RequestBody @Valid CartRequest cartRequest, @Auth Long customerId) {
        long savedId = cartService.save(cartRequest, customerId);
        return ResponseEntity.created(URI.create("/cart/" + savedId)).build();
    }

    @GetMapping
    public String viewCart() {
        return "cart";
    }

    @GetMapping("/products")
    public ResponseEntity<List<CartResponse>> viewAllCartOfCustomer(@Auth Long customerId) {
        List<CartResponse> cartResponses = cartService.findAllByCustomerId(customerId);
        return ResponseEntity.ok(cartResponses);
    }
}

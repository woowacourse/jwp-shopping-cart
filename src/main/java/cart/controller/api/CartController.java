package cart.controller.api;

import cart.auth.AuthDto;
import cart.auth.AuthPrincipal;
import cart.dto.request.CreateCartRequest;
import cart.dto.response.CartResponse;
import cart.service.CartService;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/carts")
    public ResponseEntity<List<CartResponse>> getCarts(@AuthPrincipal AuthDto authDto) {
        final List<CartResponse> productResponses = cartService.find(authDto.toDto());
        return ResponseEntity.ok(productResponses);
    }

    @PostMapping("/carts")
    public ResponseEntity<Void> addCart(@AuthPrincipal AuthDto authDto, @RequestBody @Valid final CreateCartRequest createCartRequest) {
        final Long id = cartService.insert(createCartRequest.getProductId(), authDto.toDto());
        return ResponseEntity.created(URI.create("/carts/" + id)).build();
    }

    @DeleteMapping("/carts/{id}")
    public ResponseEntity<Void> removeCart(@AuthPrincipal AuthDto authDto, @PathVariable("id") final Long id) {
        cartService.delete(id);
        return ResponseEntity.accepted().build();
    }
}

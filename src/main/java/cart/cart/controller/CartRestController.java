package cart.cart.controller;

import cart.auth.dto.UserInfo;
import cart.auth.dto.UserResponseDTO;
import cart.auth.infrastructure.AuthenticationPrincipal;
import cart.auth.service.AuthService;
import cart.cart.dto.CartRequestDTO;
import cart.cart.service.CartService;
import cart.catalog.dto.ProductResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartRestController {

    private final CartService cartService;
    private final AuthService authService;

    public CartRestController(final CartService cartService, final AuthService authService) {
        this.cartService = cartService;
        this.authService = authService;
    }

    @GetMapping("/cart/items")
    public ResponseEntity<List<ProductResponseDTO>> getCart(@AuthenticationPrincipal final UserInfo userInfo) {
        final UserResponseDTO userResponseDTO = this.authService.findUser(userInfo);
        final List<ProductResponseDTO> userCart = this.cartService.findUserCart(userResponseDTO.getId());
        return ResponseEntity.ok().body(userCart);
    }

    @PostMapping("/cart/items/{productId}")
    public ResponseEntity<Void> add(@AuthenticationPrincipal final UserInfo userInfo,
                                    @PathVariable final Long productId) {
        final UserResponseDTO userResponseDTO = this.authService.findUser(userInfo);
        final CartRequestDTO cartRequestDTO = new CartRequestDTO(userResponseDTO.getId(), productId);
        this.cartService.createCart(cartRequestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/cart/items/{productId}")
    public ResponseEntity<Void> remove(@AuthenticationPrincipal final UserInfo userInfo,
                                       @PathVariable final Long productId) {
        final UserResponseDTO userResponseDTO = this.authService.findUser(userInfo);
        final CartRequestDTO cartRequestDTO = new CartRequestDTO(userResponseDTO.getId(), productId);
        this.cartService.deleteCart(cartRequestDTO);
        return ResponseEntity.ok().build();
    }
}

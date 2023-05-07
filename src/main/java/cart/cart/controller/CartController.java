package cart.cart.controller;

import cart.auth.dto.UserInfo;
import cart.auth.dto.UserResponseDTO;
import cart.auth.infrastructure.AuthenticationPrincipal;
import cart.auth.infrastructure.BasicAuthorizationExtractor;
import cart.auth.service.AuthService;
import cart.cart.service.CartService;
import cart.catalog.dto.ResponseProductDto;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {
    
    private final CartService cartService;
    private final AuthService authService;
    private final BasicAuthorizationExtractor basicAuthorizationExtractor = new BasicAuthorizationExtractor();
    
    public CartController(final CartService cartService, final AuthService authService) {
        this.cartService = cartService;
        this.authService = authService;
    }
    
    @GetMapping("/cart/items")
    public ResponseEntity<List<ResponseProductDto>> cart(@AuthenticationPrincipal final UserInfo userInfo) {
        final UserResponseDTO userResponseDTO = this.authService.findUser(userInfo);
        final List<ResponseProductDto> userCart = this.cartService.findUserCart(userResponseDTO.getId());
        return ResponseEntity.ok().body(userCart);
    }
    
}

package cart.controller;

import cart.dto.member.MemberResponseDto;
import cart.service.CartService;
import cart.util.AuthorizationExtractor;
import cart.util.BasicAuthorizationExtractor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/carts")
@RestController
public class CartController {

    private final CartService cartService;
    private final AuthorizationExtractor<MemberResponseDto> authorizationExtractor = new BasicAuthorizationExtractor();

    private CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/{productId}")
    public ResponseEntity<Void> addCart(@PathVariable Long productId, @RequestHeader("Authorization") String authHeaderValue) {
        cartService.addCart(getMember(authHeaderValue), productId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    private MemberResponseDto getMember(final String authHeaderValue) {
        return authorizationExtractor.extractHeader(authHeaderValue);
    }
}

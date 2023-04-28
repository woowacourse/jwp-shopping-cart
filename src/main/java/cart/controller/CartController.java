package cart.controller;

import cart.dto.member.MemberLoginRequestDto;
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
    private final AuthorizationExtractor<MemberLoginRequestDto> authorizationExtractor;

    public CartController(final CartService cartService) {
        this.cartService = cartService;
        this.authorizationExtractor = new BasicAuthorizationExtractor();;
    }

    @PostMapping("/{productId}")
    public ResponseEntity<Void> addCart(@PathVariable final Long productId,
                                        @RequestHeader("Authorization") final String authHeaderValue) {
        cartService.addCart(getMember(authHeaderValue), productId);
        cartService.findAll(getMember(authHeaderValue));
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    private MemberLoginRequestDto getMember(final String authHeaderValue) {
        return authorizationExtractor.extractHeader(authHeaderValue);
    }
}

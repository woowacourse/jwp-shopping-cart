package cart.controller;

import cart.auth.AuthenticationExtractor;
import cart.auth.AuthenticationService;
import cart.auth.MemberAuthentication;
import cart.dto.MemberDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@RestController
@RequestMapping("/cart/cart-items")
public class CartItemController {

    private final AuthenticationExtractor<MemberAuthentication> authenticationExtractor;
    private final AuthenticationService authenticationService;

    public CartItemController(final AuthenticationExtractor<MemberAuthentication> authenticationExtractor, final AuthenticationService authenticationService) {
        this.authenticationExtractor = authenticationExtractor;
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public ResponseEntity<Void> postCartItems(HttpServletRequest request) {
        MemberAuthentication memberAuthentication = authenticationExtractor.extract(request);
        MemberDto memberDto = authenticationService.login(memberAuthentication);

        //Todo: 장바구니에 상품 추가 기능 구현
        return ResponseEntity.created(URI.create("/cart/cart-items/")).build();
    }
}

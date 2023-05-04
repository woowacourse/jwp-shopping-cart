package cart.controller;

import cart.dto.MemberInfo;
import cart.service.AuthService;
import cart.service.CartService;
import cart.service.MemberService;
import java.net.URI;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/carts")
public class CartController {

    private final CartService cartService;
    private final AuthService authService;
    private final MemberService memberService;

    public CartController(final CartService cartService, final AuthService authService,
                          final MemberService memberService) {
        this.cartService = cartService;
        this.authService = authService;
        this.memberService = memberService;
    }

    @PostMapping("/{productId}")
    public ResponseEntity<String> addProduct(@RequestHeader(HttpHeaders.AUTHORIZATION) String authInfo,
                                             @PathVariable Long productId) {
        final MemberInfo memberInfo = authService.extractMemberInfo(authInfo); // TODO: 2023/05/04 책임분리
        final Long memberId = memberService.findIdByMemberInfo(memberInfo);
        final Long id = cartService.addProduct(memberId, productId);
        return ResponseEntity.created(URI.create("/carts/" + id)).build();
    }
}

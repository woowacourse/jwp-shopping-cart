package cart.controller;

import cart.controller.dto.CartResponse;
import cart.service.AuthService;
import cart.service.CartService;
import cart.service.MemberService;
import cart.service.dto.MemberInfo;
import java.net.URI;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping
    public ResponseEntity<List<CartResponse>> showCart(@RequestHeader("Authorization") final String authInfo) {
        final MemberInfo memberInfo = authService.extractMemberInfo(authInfo);
        final Long memberId = memberService.findIdByMemberInfo(memberInfo);
        final List<CartResponse> products = cartService.findProductsByMemberId(memberId);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/{productId}")
    public ResponseEntity<String> addProduct(@RequestHeader(HttpHeaders.AUTHORIZATION) String authInfo,
                                             @PathVariable Long productId) {
        final MemberInfo memberInfo = authService.extractMemberInfo(authInfo);
        final Long memberId = memberService.findIdByMemberInfo(memberInfo);
        final Long id = cartService.addProduct(memberId, productId);
        return ResponseEntity.created(URI.create("/carts/" + id)).build();
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<String> deleteProduct(@RequestHeader(HttpHeaders.AUTHORIZATION) String authInfo,
                                                @PathVariable Long cartId) {
        final MemberInfo memberInfo = authService.extractMemberInfo(authInfo);
        final Long memberId = memberService.findIdByMemberInfo(memberInfo); // TODO: 2023/05/04 인증로직변경 
        cartService.deleteById(cartId);
        return ResponseEntity.noContent().build();
    }
}

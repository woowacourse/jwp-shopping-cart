package cart.controller;

import cart.dto.auth.AuthInfo;
import cart.dto.response.ResponseProductDto;
import cart.service.CartService;
import cart.service.MemberService;
import cart.ui.AuthenticationPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartApiController {

    private final CartService cartService;
    private final MemberService memberService;

    @Autowired
    public CartApiController(final CartService cartService, final MemberService memberService) {
        this.cartService = cartService;
        this.memberService = memberService;
    }

    @GetMapping("/carts")
    public ResponseEntity<List<ResponseProductDto>> getProducts(@AuthenticationPrincipal final AuthInfo authInfo) {
        final List<ResponseProductDto> cartProductsByMember = cartService.findCartProductsByMember(authInfo);
        return ResponseEntity.ok().body(cartProductsByMember);
    }

    @PutMapping("/carts/{id}")
    public ResponseEntity<Void> addProductToCart(@PathVariable final Long id, @AuthenticationPrincipal final AuthInfo authInfo) {
        final Long memberId = memberService.findIdByAuthInfo(authInfo);
        cartService.addProductToCart(memberId, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/carts/{id}")
    public ResponseEntity<Void> deleteProductFromCart(@PathVariable final Long id, @AuthenticationPrincipal final AuthInfo authInfo) {
        final Long memberId = memberService.findIdByAuthInfo(authInfo);
        cartService.deleteProductFromCart(memberId, id);
        return ResponseEntity.ok().build();
    }
}

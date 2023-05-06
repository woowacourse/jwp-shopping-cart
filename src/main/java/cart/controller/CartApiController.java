package cart.controller;

import cart.controller.authentication.AuthInfo;
import cart.controller.authentication.AuthenticationPrincipal;
import cart.domain.MemberEntity;
import cart.domain.ProductEntity;
import cart.dto.ResponseProductDto;
import cart.service.CartService;
import cart.service.MemberService;
import cart.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartApiController {

    private final MemberService memberService;
    private final CartService cartService;
    private final ProductService productService;

    public CartApiController(final MemberService memberService, final CartService cartService, final ProductService productService) {
        this.memberService = memberService;
        this.cartService = cartService;
        this.productService = productService;
    }

    @GetMapping("/carts")
    public List<ResponseProductDto> readCart(@AuthenticationPrincipal AuthInfo authInfo) {
        final MemberEntity member = memberService.findByEmail(authInfo.getEmail());
        return cartService.findCartProducts(member);
    }

    @PostMapping("/carts/{productId}")
    public ResponseEntity<Void> createCart(@AuthenticationPrincipal AuthInfo authInfo, @PathVariable final Long productId) {
        final MemberEntity member = memberService.findByEmail(authInfo.getEmail());
        final ProductEntity product = productService.findById(productId);
        cartService.insert(member, product);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/carts/{productId}")
    public ResponseEntity<Void> deleteCart(@AuthenticationPrincipal AuthInfo authInfo, @PathVariable final Long productId) {
        final MemberEntity member = memberService.findByEmail(authInfo.getEmail());
        final ProductEntity product = productService.findById(productId);
        cartService.delete(member, product);
        return ResponseEntity.ok().build();
    }
}

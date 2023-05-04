package cart.controller;

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
    public List<ResponseProductDto> readCartItem(@AuthenticationPrincipal Credentials credentials) {
        final Long memberId = memberService.findIdByEmail(credentials.getEmail());
        final List<Long> productIds = cartService.findProductIdsByMemberId(memberId);
        return productService.findByIds(productIds);
    }

    @PostMapping("/carts/{productId}")
    public ResponseEntity<Void> createCartItem(@AuthenticationPrincipal Credentials credentials, @PathVariable final Long productId) {
        final Long memberId = memberService.findIdByEmail(credentials.getEmail());
        cartService.insert(memberId, productId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/carts/{productId}")
    public ResponseEntity<Void> deleteCartItem(@AuthenticationPrincipal Credentials credentials, @PathVariable final Long productId) {
        final Long memberId = memberService.findIdByEmail(credentials.getEmail());
        cartService.delete(memberId, productId);
        return ResponseEntity.ok().build();
    }
}

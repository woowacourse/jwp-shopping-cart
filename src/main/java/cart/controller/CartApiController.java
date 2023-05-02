package cart.controller;

import cart.dto.ResponseProductDto;
import cart.service.CartService;
import cart.service.MemberService;
import cart.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
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
    public List<ResponseProductDto> readCartItem(@RequestHeader("authorization") final String authorization) {
        final Credentials credentials = getCredentials(authorization);
        final Long memberId = memberService.findIdByEmail(credentials.getEmail());
        final List<Long> productIds = cartService.findProductIdsByMemberId(memberId);
        return productService.findByIds(productIds);
    }

    private Credentials getCredentials(final String authorization) {
        if (authorization == null || !authorization.toLowerCase().startsWith("basic")) {
            throw new IllegalArgumentException();
        }
        final String base64Credentials = authorization.substring("Basic".length()).trim();
        final byte[] decodedCredentials = Base64.getDecoder().decode(base64Credentials);
        final String credentials = new String(decodedCredentials, StandardCharsets.UTF_8);
        final List<String> values = Arrays.asList(credentials.split(":", 2));

        final String email = values.get(0);
        final String password = values.get(1);

        return new Credentials(email, password);
    }

    @PostMapping("/carts/{productId}")
    public ResponseEntity<Void> createCartItem(@RequestHeader("authorization") final String authorization, @PathVariable("productId") final Long productId) {
        final Credentials credentials = getCredentials(authorization);
        final Long memberId = memberService.findIdByEmail(credentials.getEmail());
        cartService.insert(memberId, productId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/carts/{productId}")
    public ResponseEntity<Void> deleteCartItem(@RequestHeader("authorization") final String authorization, @PathVariable final Long productId) {
        final Credentials credentials = getCredentials(authorization);
        final Long memberId = memberService.findIdByEmail(credentials.getEmail());
        cartService.delete(memberId, productId);
        return ResponseEntity.ok().build();
    }
}

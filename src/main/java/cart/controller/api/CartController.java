package cart.controller.api;

import cart.auth.AuthDto;
import cart.auth.AuthorizationExtractor;
import cart.auth.BasicAuthorizationExtractor;
import cart.dto.MemberDto;
import cart.dto.response.CartResponse;
import cart.service.CartService;
import java.net.URI;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {

    private final CartService cartService;
    private final AuthorizationExtractor<AuthDto> basicAuthorizationExtractor;

    @Autowired
    public CartController(final CartService cartService, final BasicAuthorizationExtractor basicAuthorizationExtractor) {
        this.cartService = cartService;
        this.basicAuthorizationExtractor = basicAuthorizationExtractor;
    }

    @GetMapping("/carts")
    public ResponseEntity<List<CartResponse>> getCarts(final HttpServletRequest request) {
        final AuthDto authDto = basicAuthorizationExtractor.extract(request);
        final MemberDto memberDto = authDto.toMemberDto();
        final List<CartResponse> productResponses = cartService.selectCart(memberDto);
        return ResponseEntity.ok(productResponses);
    }

    @PostMapping("/carts/{productId}")
    public ResponseEntity<Void> addCart(@PathVariable("productId") final Long productId, final HttpServletRequest request) {
        final AuthDto authDto = basicAuthorizationExtractor.extract(request);
        final MemberDto memberDto = authDto.toMemberDto();
        cartService.insert(productId, memberDto);
        return ResponseEntity.created(URI.create("/carts")).build();
    }

    @DeleteMapping("/carts/{productId}")
    public ResponseEntity<Void> removeCart(@PathVariable("productId") final Long productId, final HttpServletRequest request) {
        final AuthDto authDto = basicAuthorizationExtractor.extract(request);
        final MemberDto memberDto = new MemberDto(authDto.getEmail(), authDto.getPassword());
        cartService.delete(productId, memberDto);
        return ResponseEntity.accepted().build();
    }
}

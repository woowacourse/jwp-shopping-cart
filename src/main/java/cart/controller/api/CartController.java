package cart.controller.api;

import cart.dao.entity.MemberEntity;
import cart.dto.AuthDto;
import cart.dto.response.CartResponse;
import cart.dto.response.ProductResponse;
import cart.service.CartService;
import cart.service.MemberService;
import cart.util.AuthorizationExtractor;
import cart.util.BasicAuthorizationExtractor;
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

    private final AuthorizationExtractor<AuthDto> basicAuthorizationExtractor = new BasicAuthorizationExtractor();
    private final CartService cartService;
    private final MemberService memberService;

    @Autowired
    public CartController(final CartService cartService, final MemberService memberService) {
        this.cartService = cartService;
        this.memberService = memberService;
    }

    @GetMapping("/carts")
    public ResponseEntity<List<CartResponse>> getCarts(final HttpServletRequest requestHeader) {
        final AuthDto authDto = basicAuthorizationExtractor.extract(requestHeader);
        final List<CartResponse> productResponses = cartService.selectCart(authDto);
        return ResponseEntity.ok(productResponses);
    }

    @PostMapping("/carts/{productId}")
    public ResponseEntity<Void> addCart(@PathVariable("productId") final Long productId, final HttpServletRequest requestHeader) {
        final AuthDto authDto = basicAuthorizationExtractor.extract(requestHeader);
        cartService.insert(productId, authDto);
        return ResponseEntity.created(URI.create("/carts")).build();
    }

    @DeleteMapping("/carts/{productId}")
    public ResponseEntity<Void> removeCart(@PathVariable("productId") final Long productId, final HttpServletRequest requestHeader) {
        final AuthDto authDto = basicAuthorizationExtractor.extract(requestHeader);
        cartService.delete(productId, authDto);
        return ResponseEntity.accepted().build();
    }
}

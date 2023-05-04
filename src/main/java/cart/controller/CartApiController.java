package cart.controller;

import cart.dto.auth.AuthInfo;
import cart.dto.response.ResponseProductDto;
import cart.infrastructure.BasicAuthorizationExtractor;
import cart.service.CartService;
import cart.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class CartApiController {

    private final CartService cartService;
    private final MemberService memberService;
    private final BasicAuthorizationExtractor basicAuthorizationExtractor = new BasicAuthorizationExtractor();

    @Autowired
    public CartApiController(final CartService cartService, final MemberService memberService) {
        this.cartService = cartService;
        this.memberService = memberService;
    }

    @GetMapping("/carts")
    public ResponseEntity<List<ResponseProductDto>> getProducts(final HttpServletRequest request) {
        final AuthInfo authIfo = basicAuthorizationExtractor.extract(request);
        final List<ResponseProductDto> cartProductsByMember = cartService.findCartProductsByMember(authIfo);
        return ResponseEntity.ok().body(cartProductsByMember);
    }

    @PutMapping("/cart/{id}")
    public ResponseEntity<Void> addProductToCart(@PathVariable final Long id, final HttpServletRequest request) {
        final AuthInfo authInfo = basicAuthorizationExtractor.extract(request);
        final Long memberId = memberService.findIdByAuthInfo(authInfo);
        cartService.addProductToCart(memberId, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/cart/{id}")
    public ResponseEntity<Void> deleteProductFromCart(@PathVariable final Long id, final HttpServletRequest request) {
        final AuthInfo authInfo = basicAuthorizationExtractor.extract(request);
        final Long memberId = memberService.findIdByAuthInfo(authInfo);
        cartService.deleteProductFromCart(memberId, id);
        return ResponseEntity.ok().build();
    }
}

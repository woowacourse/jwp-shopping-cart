package cart.controller;

import cart.dto.AuthInfo;
import cart.dto.ProductDto;
import cart.infrastructure.BasicAuthorizationExtractor;
import cart.service.CartService;
import cart.service.MemberService;
import java.net.URI;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final MemberService memberService;
    private final BasicAuthorizationExtractor authorizationExtractor;

    public CartController(CartService cartService, MemberService memberService) {
        this.cartService = cartService;
        this.memberService = memberService;
        this.authorizationExtractor = new BasicAuthorizationExtractor();
    }

    @PostMapping("/{productId}")
    public ResponseEntity<Object> addProductToCart(HttpServletRequest request, @PathVariable int productId) {
        AuthInfo authInfo = authorizationExtractor.extract(request);
        String email = authInfo.getEmail();
        String password = authInfo.getPassword();

        int memberId = memberService.findMemberId(email, password);

        cartService.addProduct(memberId, productId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping()
    public ResponseEntity<Object> removeProductFromCart() {
        // TODO : 멤버 id 확인
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

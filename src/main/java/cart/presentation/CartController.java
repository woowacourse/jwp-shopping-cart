package cart.presentation;

import cart.auth.AuthService;
import cart.auth.BasicAuthorizationExtractor;
import cart.auth.dto.AuthInfo;
import cart.business.CartProductService;
import cart.business.CartService;
import cart.business.MemberService;
import cart.entity.Product;
import cart.presentation.dto.ProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CartController {

    private final CartProductService cartProductService;
    private final MemberService memberService;
    private final CartService cartService;
    private BasicAuthorizationExtractor basicAuthorizationExtractor = new BasicAuthorizationExtractor();
    private AuthService authService;

    public CartController(CartProductService cartProductService, MemberService memberService, CartService cartService, AuthService authService) {
        this.cartProductService = cartProductService;
        this.memberService = memberService;
        this.cartService = cartService;
        this.authService = authService;
    }

    // 인증안에 이미 header로 memberId 혹은 email(중복 없으니까)을 주지 않을까? 이것만 알면 cart의 id를 알 필요 없을 것 같은데... 흠 ㅠ...ㅠㅜ....
    @PostMapping(path = "/cart/products/{product_id}")
    public ResponseEntity<Integer> insertProductInCart(HttpServletRequest request,
                                                       @PathVariable(value = "product_id") Integer productId) throws AuthenticationException {
        AuthInfo authInfo = basicAuthorizationExtractor.extract(request);
        String email = authInfo.getEmail();
        String password = authInfo.getPassword();
        Integer memberId = memberService.findMemberByEmail(email).get().getId();

        if (authService.checkInvalidLogin(email, password)) {
            throw new AuthenticationException("유효하지 않은 로그인 요청입니다.");
        }

        return ResponseEntity.created(URI.create("/cart/products/" + productId))
                .body(cartProductService.create(productId, memberId));
    }

    @GetMapping(path = "/cart/products")
    public ResponseEntity<List<ProductResponse>> readProducts(HttpServletRequest request) throws AuthenticationException {
        String email = checkValidLogin(request);
        Integer memberId = memberService.findMemberByEmail(email).get().getId();
        List<Product> products = cartService.findProductsByMemberId(memberId);

        List<ProductResponse> response = products.stream()
                .map(product -> new ProductResponse(product.getId(),
                        product.getName(),
                        product.getUrl(),
                        product.getPrice()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/cart/products/{product_id}")
    public ResponseEntity<Integer> delete(
            HttpServletRequest request, @PathVariable(value = "product_id") Integer productId) throws AuthenticationException {
        checkValidLogin(request);

        return ResponseEntity.ok().body(cartProductService.delete(productId));
    }

    private String checkValidLogin(HttpServletRequest request) throws AuthenticationException {
        AuthInfo authInfo = basicAuthorizationExtractor.extract(request);
        String email = authInfo.getEmail();
        String password = authInfo.getPassword();

        if (authService.checkInvalidLogin(email, password)) {
            throw new AuthenticationException("유효하지 않은 로그인 요청입니다.");
        }

        return email;
    }
}

package cart.presentation;

import cart.auth.AuthService;
import cart.business.CartService;
import cart.business.MemberService;
import cart.business.ProductService;
import cart.entity.ProductEntity;
import cart.presentation.dto.ProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CartController {

    private final ProductService productService;
    private final MemberService memberService;
    private final CartService cartService;
    //private BasicAuthorizationExtractor basicAuthorizationExtractor = new BasicAuthorizationExtractor();
    private AuthService authService;

    public CartController(ProductService productService, MemberService memberService, CartService cartService, AuthService authService) {
        this.productService = productService;
        this.memberService = memberService;
        this.cartService = cartService;
        this.authService = authService;
    }

    // 인증안에 이미 header로 memberId 혹은 email(중복 없으니까)을 주지 않을까? 이것만 알면 cart의 id를 알 필요 없을 것 같은데... 흠 ㅠ...ㅠㅜ....
    @PostMapping(path = "/cart/products/{product_id}")
    public ResponseEntity<Integer> addProductInCart(HttpServletRequest request,
                                                    @PathVariable(value = "product_id") Integer productId) {
        Integer memberId = (Integer) request.getAttribute("member_id");

        return ResponseEntity.created(URI.create("/cart/products/" + productId))
                .body(productService.createProductInCart(productId, memberId));
    }

    @GetMapping(path = "/cart/products")
    public ResponseEntity<List<ProductResponse>> getProducts(HttpServletRequest request) {
        Integer memberId = (Integer) request.getAttribute("member_id");
        List<ProductEntity> products = cartService.findProductsByMemberId(memberId);

        List<ProductResponse> response = products.stream()
                .map(product -> new ProductResponse(product.getId(),
                        product.getName(),
                        product.getUrl(),
                        product.getPrice()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/cart/products/{product_id}")
    public ResponseEntity<Integer> removeProduct(
            HttpServletRequest request, @PathVariable(value = "product_id") Integer productId) {
        String email = request.getAttribute("email").toString();
        Integer memberId = memberService.findMemberByEmail(email).get().getId();

        return ResponseEntity.ok().body(productService.deleteProductInCart(memberId, productId));
    }
}

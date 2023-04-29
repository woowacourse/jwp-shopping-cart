package cart.controller.api;

import cart.dto.request.cart.CartAddRequest;
import cart.dto.request.cart.CartDeleteRequest;
import cart.dto.product.ProductDto;
import cart.dto.response.Response;
import cart.dto.response.ResultResponse;
import cart.dto.response.SimpleResponse;
import cart.service.BasicAuthService;
import cart.service.CartService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
public class CartApiController {
    private static final String AUTHORIZATION_HEADER = "authorization";
    private final CartService cartService;
    private final BasicAuthService basicAuthService;

    public CartApiController(CartService cartService, BasicAuthService basicAuthService) {
        this.cartService = cartService;
        this.basicAuthService = basicAuthService;
    }

    @PostMapping
    public ResponseEntity<Response> addProductToCart(@RequestBody @Valid CartAddRequest cartAddRequest,
                                                     HttpServletRequest request) {
        Long memberId = basicAuthService.resolveMemberId(request.getHeader(AUTHORIZATION_HEADER));
        cartService.addToCart(memberId, cartAddRequest.getProductId());
        return ResponseEntity.ok()
                .body(SimpleResponse.ok("장바구니에 상품이 담겼습니다."));
    }

    @GetMapping
    public ResponseEntity<Response> findAllProductsByMemberId(HttpServletRequest request) {
        Long memberId = basicAuthService.resolveMemberId(request.getHeader(AUTHORIZATION_HEADER));
        List<ProductDto> allProducts = cartService.findAllProducts(memberId);
        return ResponseEntity.ok()
                .body(ResultResponse.ok(allProducts.size() + "개의 상품이 조회되었습니다.", allProducts));
    }

    @DeleteMapping
    public ResponseEntity<Response> deleteProductToCart(@RequestBody @Valid CartDeleteRequest cartDeleteRequest,
                                                        HttpServletRequest request) {
        Long memberId = basicAuthService.resolveMemberId(request.getHeader(AUTHORIZATION_HEADER));
        cartService.deleteProduct(memberId, cartDeleteRequest.getProductId());
        return ResponseEntity.ok()
                .body(SimpleResponse.ok("장바구니에 상품이 삭제되었습니다."));
    }
}

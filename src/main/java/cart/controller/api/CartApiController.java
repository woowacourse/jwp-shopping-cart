package cart.controller.api;

import cart.dto.cart.CartProductDto;
import cart.dto.response.Response;
import cart.dto.response.ResultResponse;
import cart.dto.response.SimpleResponse;
import cart.infrastructure.Principal;
import cart.infrastructure.User;
import cart.service.CartService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
public class CartApiController {
    private final CartService cartService;

    public CartApiController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/{productId:[1-9]\\d*}")
    public ResponseEntity<Response> addProductToCart(@PathVariable Long productId, @Principal User user) {
        cartService.addToCart(user.getMemberId(), productId);
        return ResponseEntity.ok()
                .body(SimpleResponse.ok("장바구니에 상품이 담겼습니다."));
    }

    @GetMapping
    public ResponseEntity<Response> findAllCartProducts(@Principal User user) {
        List<CartProductDto> allProducts = cartService.findAllCartProducts(user.getMemberId());
        return ResponseEntity.ok()
                .body(ResultResponse.ok(allProducts.size() + "개의 상품이 조회되었습니다.", allProducts));
    }

    @DeleteMapping("/{cartId:[1-9]\\d*}")
    public ResponseEntity<Response> deleteProductToCart(@PathVariable Long cartId, @Principal User user) {
        cartService.deleteProduct(user.getMemberId(), cartId);
        return ResponseEntity.ok()
                .body(SimpleResponse.ok("장바구니에 상품이 삭제되었습니다."));
    }
}

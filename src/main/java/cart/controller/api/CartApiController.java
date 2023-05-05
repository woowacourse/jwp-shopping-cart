package cart.controller.api;

import cart.config.AuthenticationPrincipal;
import cart.controller.dto.CartResponse;
import cart.controller.dto.MemberRequest;
import cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartApiController {

    private final CartService cartService;

    public CartApiController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartResponse>> cart(@AuthenticationPrincipal MemberRequest memberRequest) {
        List<CartResponse> carts = cartService.getCartProductByMember(memberRequest);
        return ResponseEntity.ok(carts);
    }

    @PostMapping("{productId}")
    public ResponseEntity<Long> addCart(
            @PathVariable Long productId,
            @AuthenticationPrincipal MemberRequest memberRequest
    ) {
        Long id = cartService.addCart(productId, memberRequest);
        return ResponseEntity.ok(id);
    }

    //TODO: 지우려고 선택한 카트아이디의 회원 정보와 요청을 보낸 회원 정보가 일치하는지 검증이 필요할까?
    // TODO: 컨트롤러 서비스 dao 들의 함수 네이밍이 자꾸 겹치는데..
    @DeleteMapping("{cartId}")
    public void deleteCart(
            @PathVariable Long cartId
    ) {
        cartService.deleteCart(cartId);
    }

    //TODO
    /**
     * ResponseEntity<Void>
     * return ResponseEntity.noContent().build(); -> 알아보기
     */
}

package cart.controller.api;

import cart.annotation.Authenticate;
<<<<<<< HEAD
<<<<<<< HEAD
import cart.controller.dto.request.ProductIdRequest;
import cart.controller.dto.response.CartItemResponse;
import cart.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
=======
import cart.controller.dto.request.MemberRequest;
=======
>>>>>>> e07c1629 (feat: 사용자 인증 처리 구현)
import cart.controller.dto.request.ProductIdRequest;
import cart.controller.dto.response.CartItemResponse;
import cart.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> ed825fb4 (feat: 장바구니에 상품 추가)
=======
import org.springframework.web.bind.annotation.GetMapping;
>>>>>>> 28a6d971 (feat: findAllByMemberId 구현)
=======
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
>>>>>>> 46ded3a7 (feat: 장바구니 상품 삭제)
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
<<<<<<< HEAD
<<<<<<< HEAD
import java.util.List;
=======
>>>>>>> ed825fb4 (feat: 장바구니에 상품 추가)
=======
import java.util.List;
>>>>>>> 28a6d971 (feat: findAllByMemberId 구현)

@RestController
@RequestMapping("/cart")
public class CartApiController {

    private final CartService cartService;

    public CartApiController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/product")
    public ResponseEntity<Void> save(
<<<<<<< HEAD
<<<<<<< HEAD
            @Authenticate final Long memberId,
=======
            final Long memberId,
>>>>>>> ed825fb4 (feat: 장바구니에 상품 추가)
=======
            @Authenticate final Long memberId,
>>>>>>> e07c1629 (feat: 사용자 인증 처리 구현)
            @RequestBody @Valid final ProductIdRequest productIdRequest
    ) {
        cartService.save(memberId, productIdRequest);
        return ResponseEntity.ok().build();
    }

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 28a6d971 (feat: findAllByMemberId 구현)
    @GetMapping("/products")
    public ResponseEntity<List<CartItemResponse>> findAll(@Authenticate final Long memberId) {
        List<CartItemResponse> responses = cartService.findAll(memberId);
        return ResponseEntity.ok(responses);
    }

<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 46ded3a7 (feat: 장바구니 상품 삭제)
    @DeleteMapping("/product/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        cartService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
<<<<<<< HEAD
=======

>>>>>>> ed825fb4 (feat: 장바구니에 상품 추가)

=======
>>>>>>> db0c1803 (feat: CartDao save 테스트)
=======
>>>>>>> 28a6d971 (feat: findAllByMemberId 구현)
=======

>>>>>>> 46ded3a7 (feat: 장바구니 상품 삭제)
}

package cart.controller;

import cart.dao.entity.CartEntity;
import cart.dto.AddCartRequestDto;
import cart.dto.auth.AuthInfo;
import cart.dto.response.ResponseCartProductDto;
import cart.service.CartService;
import cart.service.MemberService;
import cart.ui.AuthenticationPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CartApiController {

    private final CartService cartService;
    private final MemberService memberService;

    @Autowired
    public CartApiController(final CartService cartService, final MemberService memberService) {
        this.cartService = cartService;
        this.memberService = memberService;
    }

    @GetMapping("/carts")
    public ResponseEntity<List<ResponseCartProductDto>> getProducts(@AuthenticationPrincipal final AuthInfo authInfo) {
        final List<CartEntity> cartEntities = cartService.findCarts(authInfo);
        final List<ResponseCartProductDto> cartProductDtos = cartEntities.stream()
                .map(cartEntity -> ResponseCartProductDto.of(cartEntity.getId(), cartService.findProduct(cartEntity.getProductId())))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(cartProductDtos);
    }

    @PostMapping("/carts")
    public ResponseEntity<Void> addProductToCart(@RequestBody @Valid final AddCartRequestDto addCartRequestDto, @AuthenticationPrincipal final AuthInfo authInfo) {
        final Long memberId = memberService.findIdByAuthInfo(authInfo);
        cartService.addProductToCart(addCartRequestDto, memberId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/carts/{id}")
    public ResponseEntity<Void> deleteProductFromCart(@PathVariable final Long id, @AuthenticationPrincipal final AuthInfo authInfo) {
        cartService.deleteProductFromCart(id, authInfo);
        return ResponseEntity.ok().build();
    }
}

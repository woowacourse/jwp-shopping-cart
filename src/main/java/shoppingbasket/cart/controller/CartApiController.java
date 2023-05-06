package shoppingbasket.cart.controller;

import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shoppingbasket.auth.AuthInfo;
import shoppingbasket.auth.AuthenticationPrincipal;
import shoppingbasket.auth.BasicAuthorizationExtractor;
import shoppingbasket.cart.dto.CartInsertRequestDto;
import shoppingbasket.cart.dto.CartSelectResponseDto;
import shoppingbasket.cart.entity.CartEntity;
import shoppingbasket.cart.service.CartService;
import shoppingbasket.member.service.MemberService;

@RestController
public class CartApiController {

    private final CartService cartService;
    private final MemberService memberService;
    private final BasicAuthorizationExtractor authorizationExtractor;

    public CartApiController(final CartService cartService,
                             final MemberService memberService,
                             final BasicAuthorizationExtractor authorizationExtractor) {
        this.cartService = cartService;
        this.memberService = memberService;
        this.authorizationExtractor = authorizationExtractor;
    }

    @PostMapping("/cart")
    public ResponseEntity<CartEntity> addProduct(@AuthenticationPrincipal AuthInfo authInfo,
                                                 @RequestBody @Valid CartInsertRequestDto insertRequestDto) {

        final int productId = insertRequestDto.getProductId();
        final CartEntity cart = cartService.addCart(authInfo.getEmail(), productId);
        final int savedId = cart.getId();

        return ResponseEntity.created(URI.create("/cart/" + savedId))
                .body(cart);
    }

    @GetMapping("/cart/{id}")
    public ResponseEntity<CartSelectResponseDto> getCartById(@AuthenticationPrincipal AuthInfo authInfo,
                                                             @PathVariable int id) {

        CartSelectResponseDto cartSelectResponseDto = cartService.getCartById(id);

        return ResponseEntity.ok(cartSelectResponseDto);
    }

    @GetMapping(value = "/cart", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CartSelectResponseDto>> getCarts(@AuthenticationPrincipal AuthInfo authInfo) {
        final String memberEmail = authInfo.getEmail();
        final List<CartSelectResponseDto> cartSelectResponse = cartService.getCartsByMemberEmail(memberEmail);
        return ResponseEntity.ok(cartSelectResponse);
    }

    @DeleteMapping("/cart/{id}")
    public ResponseEntity<Void> removeCart(@AuthenticationPrincipal AuthInfo authInfo,
                                           @PathVariable int id) {

        cartService.removeCart(id);
        return ResponseEntity.noContent().build();
    }
}

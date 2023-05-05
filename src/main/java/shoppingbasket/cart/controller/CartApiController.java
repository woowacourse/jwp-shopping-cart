package shoppingbasket.cart.controller;

import shoppingbasket.auth.AuthInfo;
import shoppingbasket.auth.BasicAuthorizationExtractor;
import shoppingbasket.cart.dto.CartDeleteResponseDto;
import shoppingbasket.cart.dto.CartInsertRequestDto;
import shoppingbasket.cart.dto.CartSelectResponseDto;
import shoppingbasket.cart.entity.CartEntity;
import shoppingbasket.cart.service.CartService;
import shoppingbasket.exception.PasswordMismatchException;
import shoppingbasket.exception.UnauthenticatedException;
import shoppingbasket.member.entity.MemberEntity;
import shoppingbasket.member.service.MemberService;
import java.net.URI;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<CartEntity> addProduct(HttpServletRequest request,
                                                 @RequestBody @Valid CartInsertRequestDto insertRequestDto) {

        final AuthInfo authInfo = authorizationExtractor.extract(request);
        checkAuth(authInfo);
        MemberEntity member = memberService.findMemberByEmail(authInfo.getEmail());
        validatePassword(member.getPassword(), authInfo.getPassword());

        final int productId = insertRequestDto.getProductId();
        final CartEntity cart = cartService.addCart(member, productId);
        final int savedId = cart.getId();

        return ResponseEntity.created(URI.create("/shoppingbasket/" + savedId))
                .body(cart);
    }

    private void checkAuth(final AuthInfo authInfo) {
        if (authInfo == null) {
            throw new UnauthenticatedException("사용자가 선택되지 않았습니다.");
        }
    }

    private void validatePassword(final String memberPassword, final String authPassword) {
        if (!memberPassword.equals(authPassword)) {
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }
    }

    @GetMapping(value = "/cart", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CartSelectResponseDto>> getCart(HttpServletRequest request) {
        final AuthInfo authInfo = authorizationExtractor.extract(request);
        checkAuth(authInfo);
        MemberEntity member = memberService.findMemberByEmail(authInfo.getEmail());
        validatePassword(member.getPassword(), authInfo.getPassword());

        final List<CartSelectResponseDto> cartSelectResponse = cartService.getCartByMemberID(member.getId());
        return ResponseEntity.ok(cartSelectResponse);
    }

    @DeleteMapping("/cart/{id}")
    public ResponseEntity<CartDeleteResponseDto> removeCart(HttpServletRequest request, @PathVariable int id) {
        final AuthInfo authInfo = authorizationExtractor.extract(request);
        checkAuth(authInfo);
        MemberEntity member = memberService.findMemberByEmail(authInfo.getEmail());
        validatePassword(member.getPassword(), authInfo.getPassword());

        final CartDeleteResponseDto deleteResponseDto = cartService.removeCart(id);
        return ResponseEntity.ok(deleteResponseDto);
    }
}

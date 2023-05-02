package cart.cart.controller;

import cart.auth.AuthInfo;
import cart.auth.BasicAuthorizationExtractor;
import cart.cart.dto.CartDeleteResponseDto;
import cart.cart.dto.CartInsertRequestDto;
import cart.cart.dto.CartInsertResponseDto;
import cart.cart.dto.CartSelectResponseDto;
import cart.cart.service.CartService;
import cart.member.entity.MemberEntity;
import cart.member.service.MemberService;
import java.net.URI;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
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
    public ResponseEntity<CartInsertResponseDto> addProduct(HttpServletRequest request,
                                                            @RequestBody CartInsertRequestDto insertRequestDto) {

        final AuthInfo authInfo = authorizationExtractor.extract(request);
        checkAuth(authInfo);
        MemberEntity member = memberService.findMemberByEmail(authInfo.getEmail());
        validatePassword(member.getPassword(), authInfo.getPassword());

        final int productId = insertRequestDto.getProductId();
        final CartInsertResponseDto cartInsertResponseDto = cartService.addCart(member, productId);
        final int savedId = cartInsertResponseDto.getId();

        return ResponseEntity.created(URI.create("/cart/" + savedId))
                .body(cartInsertResponseDto);
    }

    private void checkAuth(final AuthInfo authInfo) {
        if (authInfo == null) {
            throw new IllegalArgumentException("사용자가 선택되지 않았습니다.");
        }
    }

    private void validatePassword(final String memberPassword, final String authPassword) {
        if (!memberPassword.equals(authPassword)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
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

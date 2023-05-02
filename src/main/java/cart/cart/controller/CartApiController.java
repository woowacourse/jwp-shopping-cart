package cart.cart.controller;

import cart.auth.AuthInfo;
import cart.auth.BasicAuthorizationExtractor;
import cart.cart.dto.CartInsertResponseDto;
import cart.cart.service.CartService;
import cart.member.entity.MemberEntity;
import cart.member.service.MemberService;
import java.net.URI;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseEntity<CartInsertResponseDto> addProduct(HttpServletRequest request, int productId) {
        final AuthInfo authInfo = authorizationExtractor.extract(request);
        if (authInfo == null) {
            throw new IllegalArgumentException("사용자가 선택되지 않았습니다.");
        }

        final MemberEntity member = memberService.findMemberByEmail(authInfo.getEmail());
        if (!member.getPassword().equals(authInfo.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        final CartInsertResponseDto cartInsertResponseDto = cartService.addCart(member, productId);
        final int savedId = cartInsertResponseDto.getId();

        return ResponseEntity.created(URI.create("/cart/" + savedId))
                .body(cartInsertResponseDto);
    }

}

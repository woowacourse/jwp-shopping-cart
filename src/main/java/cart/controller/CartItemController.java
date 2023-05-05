package cart.controller;

import cart.domain.cartitem.CartItem;
import cart.domain.cartitem.CartItemService;
import cart.domain.member.Member;
import cart.domain.member.MemberService;
import cart.dto.AuthInfo;
import cart.infratstructure.BasicAuthorizationExtractor;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cartitems")
public class CartItemController {

    private final MemberService memberService;
    private final CartItemService cartItemService;
    private final BasicAuthorizationExtractor basicAuthorizationExtractor = new BasicAuthorizationExtractor();

    public CartItemController(final MemberService memberService, final CartItemService cartItemService) {
        this.memberService = memberService;
        this.cartItemService = cartItemService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<CartItem> find(HttpServletRequest request) {
        AuthInfo authInfo = basicAuthorizationExtractor.extract(request);
        String email = authInfo.getEmail();
        String password = authInfo.getPassword();

        Optional<Member> foundMember = memberService.findByEmail(email);
        // TODO 사용자 검증 (null 체크, 비밀번호 확인)
        Member member = foundMember.get();

        return List.of(new CartItem(1L, "치킨", "xxx", 10000));
    }
}

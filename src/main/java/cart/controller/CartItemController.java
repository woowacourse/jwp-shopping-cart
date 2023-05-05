package cart.controller;

import cart.domain.cartitem.CartItem;
import cart.domain.cartitem.CartItemService;
import cart.domain.member.Member;
import cart.domain.member.MemberService;
import cart.dto.AuthInfo;
import cart.infratstructure.BasicAuthorizationExtractor;
import java.util.Objects;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{productId}")
    public void create(@PathVariable Long productId, HttpServletRequest request) {
        Long memberId = findMemberId(request);
        cartItemService.add(new CartItem(memberId, productId));
    }

    private Long findMemberId(HttpServletRequest request) {
        AuthInfo authInfo = basicAuthorizationExtractor.extract(request);
        if (authInfo == null) {
            throw new IllegalArgumentException("사용자 정보가 존재하지 않습니다");
        }
        String email = authInfo.getEmail();
        String password = authInfo.getPassword();

        Optional<Member> foundMember = memberService.findByEmail(email);
        if (foundMember.isEmpty()) {
            throw new IllegalArgumentException("잘못된 사용자 정보입니다");
        }
        Member member = foundMember.get();
        if (Objects.equals(email, member.getEmail()) && Objects.equals(password, member.getPassword())) {
            return member.getId();
        }
        throw new IllegalArgumentException("잘못된 사용자 정보입니다");
    }
}

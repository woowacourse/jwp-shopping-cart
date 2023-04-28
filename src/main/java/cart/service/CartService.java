package cart.service;

import cart.dto.member.MemberResponseDto;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final MemberService memberService;

    private CartService(final MemberService memberService) {
        this.memberService = memberService;
    }

    public void addCart(final MemberResponseDto memberResponseDto, final Long productId) {
        // TODO : 장바구니 채우기
    }
}

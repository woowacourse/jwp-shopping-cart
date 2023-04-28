package cart.service;

import cart.domain.member.Member;
import cart.dto.member.MemberLoginRequestDto;
import cart.dto.member.MemberResponseDto;
import cart.dto.member.MembersResponseDto;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final MemberService memberService;

    private CartService(final MemberService memberService) {
        this.memberService = memberService;
    }

    public void addCart(final MemberLoginRequestDto memberLoginRequestDto, final Long productId) {
        Member member = memberService.findMember(memberLoginRequestDto);


    }
}

package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.member.application.MemberService;

@Service
public class AuthService {

    private final MemberService memberService;

    public AuthService(MemberService memberService) {
        this.memberService = memberService;
    }

    public TokenResponse generateToken(TokenRequest tokenRequest) {
        memberService.isLogin(tokenRequest);
        return null;
    }
}

package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.member.application.MemberService;
import woowacourse.member.domain.Member;

@Transactional
@Service
public class AuthService {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(MemberService memberService, JwtTokenProvider jwtTokenProvider) {
        this.memberService = memberService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public TokenResponse generateToken(TokenRequest tokenRequest) {
        Member member = memberService.login(tokenRequest);
        return new TokenResponse(jwtTokenProvider.createToken(String.valueOf(member.getId())));
    }
}

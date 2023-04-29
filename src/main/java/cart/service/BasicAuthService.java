package cart.service;

import cart.exception.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

@Service
public class BasicAuthService {
    private static final String BASIC_HEADER = "Basic ";
    private static final String TOKEN_DELIMITER = ":";
    private static final int EMAIL_INDEX = 0;
    private static final int PASSWORD_INDEX = 1;
    private static final int TOKEN_LENGTH = 2;

    private final MemberService memberService;

    public BasicAuthService(MemberService memberService) {
        this.memberService = memberService;
    }

    public Long resolveMemberId(String basicToken) {
        if (StringUtils.hasText(basicToken)) {
            String decodedToken = decodeToken(getToken(basicToken));
            return getMemberId(decodedToken);
        }
        throw new AuthenticationException("인증 토큰이 비어있습니다.");
    }

    private String getToken(String basicToken) {
        if (basicToken.startsWith(BASIC_HEADER)) {
            return StringUtils.delete(basicToken, BASIC_HEADER);
        }
        throw new AuthenticationException("베이직 형식의 토큰이 아닙니다.");
    }

    private String decodeToken(String token) {
        return new String(Base64Utils.decodeFromString(token));
    }

    private Long getMemberId(String token) {
        String[] tokens = token.split(TOKEN_DELIMITER);
        validateToken(tokens);
        String email = tokens[EMAIL_INDEX];
        String password = tokens[PASSWORD_INDEX];
        return memberService.findIdByEmailAndPassword(email, password);
    }

    private void validateToken(String[] tokens) {
        if (tokens.length != TOKEN_LENGTH) {
            throw new AuthenticationException("올바른 형식의 토큰이 아닙니다.");
        }
    }
}

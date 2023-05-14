package cart.auth;

import cart.excpetion.AuthenticationException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BasicAuthorizationExtractor {

    private static final String TOKEN_TYPE = "Basic";
    private static final String DELIMITER = ":";
    private static final int TOKEN_INFO_COUNT = 2;
    private static final int EMAIL_INDEX = 0;
    private static final int PASSWORD_INDEX = 1;

    public LoginRequest extract(final String loginToken) {
        final String tokenValue = tokenValue(loginToken);
        final List<String> split = split(tokenValue);
        return new LoginRequest(
                split.get(EMAIL_INDEX),
                split.get(PASSWORD_INDEX)
        );
    }

    private String tokenValue(final String loginToken) {
        final boolean startWithBasic = loginToken.toLowerCase().startsWith(TOKEN_TYPE.toLowerCase());
        if (!startWithBasic) {
            throw new AuthenticationException("로그인 요청은 Basic 타입만 가능합니다");
        }
        String authHeaderValue = loginToken.substring(TOKEN_TYPE.length()).trim();
        byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
        return new String(decodedBytes);
    }

    private List<String> split(final String tokenValue) {
        final List<String> splitedvalue = List.of(tokenValue.split(DELIMITER));
        if (splitedvalue.size() != TOKEN_INFO_COUNT) {
            throw new AuthenticationException("토큰에는 아이디, 비밀번호가 입력되어야 합니다.");
        }
        return splitedvalue;
    }
}

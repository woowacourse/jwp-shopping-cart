package woowacourse.auth.support;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtTokenProviderTest {

    private final String email = "roma@naver.com";
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("토큰 payload 반환 기능을 검증한다.")
    void getPayload() {
        //when
        String token = jwtTokenProvider.createToken(email);
        String actual = jwtTokenProvider.getPayload(token);

        //then
        assertThat(actual).isEqualTo(email);
    }

    @Test
    @DisplayName("토큰의 마지막 글자를 수정한 경우 검증에 실패한다.")
    void validateToken_false() {
        //when
        String token = jwtTokenProvider.createToken(email);
        String manipulatedToken = manipulateToken(token);

        //then
        boolean actual = jwtTokenProvider.validateToken(manipulatedToken);
        assertThat(actual).isFalse();
    }

    private String manipulateToken(String token) {
        if (token.charAt(0) == '1') {
            return token.substring(1) + "0";
        }
        return token.substring(1) + "1";
    }

    @Test
    @DisplayName("유효한 토큰인 경우 검증에 성공한다.")
    void validateToken_true() {
        //when
        String token = jwtTokenProvider.createToken(email);

        //then
        boolean actual = jwtTokenProvider.validateToken(token);
        assertThat(actual).isTrue();
    }
}

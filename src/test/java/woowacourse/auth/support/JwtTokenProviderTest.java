package woowacourse.auth.support;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("JwtToken 생성 검증")
    void createToken() {
        String token = jwtTokenProvider.createToken("green");

        assertThat(token).isNotNull();
    }

    @Test
    @DisplayName("JwtToken 유효한 토큰 검증 - true")
    void validateTrueToken() {
        String token = jwtTokenProvider.createToken("green");

        assertThat(jwtTokenProvider.validateToken(token)).isTrue();
    }

    @Test
    @DisplayName("JwtToken 유효한 토큰 검증 - false")
    void validateFalseToken() {
        assertThat(jwtTokenProvider.validateToken("green")).isFalse();
    }

    @Test
    @DisplayName("JwtToken payload 검증")
    void getPayload() {
        String token = jwtTokenProvider.createToken("green");

        assertThat(jwtTokenProvider.getPayload(token)).isEqualTo("green");
    }
}
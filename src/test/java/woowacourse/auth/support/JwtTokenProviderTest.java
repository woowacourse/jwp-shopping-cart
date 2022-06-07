package woowacourse.auth.support;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.exception.AuthorizationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
@Transactional
class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @DisplayName("토큰이 올바르게 생성된다.")
    @Test
    void createToken() {
        String payload = "kth990303";

        String token = jwtTokenProvider.createToken(payload);

        assertThat(token).isNotNull();
    }

    @DisplayName("올바른 토큰 정보로 payload를 조회한다.")
    @Test
    void getPayloadByValidToken() {
        String payload = "forky";

        String token = jwtTokenProvider.createToken(payload);

        assertThat(jwtTokenProvider.getPayload(token)).isEqualTo(payload);
    }

    @DisplayName("올바르지 않은 토큰 정보로 payload를 조회할 경우 예외를 발생시킨다.")
    @Test
    void getPayloadByInvalidToken() {
        assertThatExceptionOfType(AuthorizationException.class)
                .isThrownBy(() -> jwtTokenProvider.getPayload("invalidToken"))
                .withMessageContaining("로그인");
    }
}
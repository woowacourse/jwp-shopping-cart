package woowacourse.auth.support;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JwtTokenProviderTest {

    private static final JwtTokenProvider TOKEN_PROVIDER = new JwtTokenProvider(
            "7KuM7LmY64qU7J6l67CU6rWs64uI", 3600000);

    @Test
    @DisplayName("문자열을 토큰으로 만든다.")
    void createToken() {
        // given
        final String id = "1";

        // when
        final String token = TOKEN_PROVIDER.createToken(id);

        // then
        assertAll(
                () -> assertThat(token).isNotNull(),
                () -> assertThat(TOKEN_PROVIDER.getPayload(token)).isEqualTo("1")
        );
    }

    @Test
    @DisplayName("토큰 유효성을 검사한다.")
    void validateToken() {
        // given
        final String id = "1";

        // when
        final String token = TOKEN_PROVIDER.createToken(id);

        // then
        assertAll(
                () -> assertThat(token).isNotNull(),
                () -> assertThat(TOKEN_PROVIDER.validateToken(token)).isTrue()
        );
    }
}

package cart.common.auth;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import cart.exception.UnAuthorizedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class BasicTokenProviderTest {

    @Test
    @DisplayName("정상적인 Authorization 헤더 정보면, 디코딩된 토큰 정보를 반환한다.")
    void extractToken_success() {
        // given
        final String authorization = "Basic am91cm5leUBnbWFpbC5jb206cGFzc3dvcmQ=";

        // when
        final String token = BasicTokenProvider.extractToken(authorization);

        // then
        assertThat(token)
            .isEqualTo("journey@gmail.com:password");
    }

    @NullSource
    @ParameterizedTest(name = "Authorization 헤더 정보가 null이면, 예외가 발생한다.")
    void extractToken_null_fail(final String authorization) {
        assertThatThrownBy(() -> BasicTokenProvider.extractToken(authorization))
            .isInstanceOf(UnAuthorizedException.class);
    }

    @ValueSource(strings = {"", "Basic"})
    @ParameterizedTest(name = "Authorization 헤더 정보가 Basic prefix를 포함할 만큼 길이가 길지 않으면, 예외가 발생한다.")
    void extractToken_length_fail(final String authorization) {
        assertThatThrownBy(() -> BasicTokenProvider.extractToken(authorization))
            .isInstanceOf(UnAuthorizedException.class);
    }
}

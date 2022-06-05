package woowacourse.auth.support;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import woowacourse.auth.exception.InvalidTokenException;
import woowacourse.auth.exception.NotAuthorizationException;

class AuthorizationExtractorTest {

    @DisplayName("Authorization 헤더에 Bearer 타입의 토큰이 있는 경우 토큰을 반환한다.")
    @Test
    void extract() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer accessToken");

        assertThat(AuthorizationExtractor.extract(request)).isEqualTo("accessToken");
    }

    @DisplayName("Authorization 헤더가 없는 경우 예외가 발생한다.")
    @Test
    void extractWithoutAuthorizationHeader() {
        MockHttpServletRequest request = new MockHttpServletRequest();

        assertThatThrownBy(() -> AuthorizationExtractor.extract(request))
                .isExactlyInstanceOf(NotAuthorizationException.class)
                .hasMessageContaining("로그인이 필요합니다.");
    }

    @DisplayName("Authorization 헤더에 Bearer 타입의 토큰이 없는 경우 예외가 발생한다.")
    @Test
    void extractWithoutBearerTypeToken() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "asd");

        assertThatThrownBy(() -> AuthorizationExtractor.extract(request))
                .isExactlyInstanceOf(InvalidTokenException.class)
                .hasMessageContaining("유효하지 않은 토큰입니다.");
    }
}

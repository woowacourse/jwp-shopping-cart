package cart.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.dto.MemberAuthDto;
import cart.exception.AuthenticationException;
import java.util.Base64;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class BasicAuthenticationExtractorTest {

    private static final BasicAuthenticationExtractor basicAuthenticationExtractor = new BasicAuthenticationExtractor();

    @Nested
    @DisplayName("인증 토큰에서 정보 추출 시 ")
    class Extract {

        @Test
        @DisplayName("유효한 인증 토큰이라면 정보를 추출한다.")
        void extract() {
            final String authorization = new String(Base64.getEncoder().encode("a@a.com:password1".getBytes()));
            final String basicToken = "Basic " + authorization;

            final MemberAuthDto result = basicAuthenticationExtractor.extract(basicToken);

            assertAll(
                    () -> assertThat(result.getEmail()).isEqualTo("a@a.com"),
                    () -> assertThat(result.getPassword()).isEqualTo("password1")
            );
        }

        @Test
        @DisplayName("인증 토큰이 null이라면 예외를 던진다.")
        void extractWithNullToken() {
            assertThatThrownBy(() -> basicAuthenticationExtractor.extract(null))
                    .isInstanceOf(AuthenticationException.class)
                    .hasMessage("인증 정보가 존재하지 않습니다.");
        }

        @ParameterizedTest
        @CsvSource(value = {"Bearer 123", "Basic "})
        @DisplayName("Basic 토큰 형식에 알맞지 않으면 예외를 던진다.")
        void extractWithInvalidType(final String basicToken) {
            assertThatThrownBy(() -> basicAuthenticationExtractor.extract(basicToken))
                    .isInstanceOf(AuthenticationException.class)
                    .hasMessage("유효하지 않은 인증 정보 형식입니다.");
        }

        @Test
        @DisplayName("디코딩 시 이메일, 패스워드 모두 존재하지 않으면 예외를 던진다.")
        void extractWithInvalidFieldCount() {
            final String authorization = new String(Base64.getEncoder().encode("a@a.com".getBytes()));
            final String basicToken = "Basic " + authorization;

            assertThatThrownBy(() -> basicAuthenticationExtractor.extract(basicToken))
                    .isInstanceOf(AuthenticationException.class)
                    .hasMessage("유효하지 않은 인증 정보입니다.");
        }
    }
}

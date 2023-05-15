package cart.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Base64;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class BasicAuthorizationParserTest {

    private final BasicAuthorizationParser basicAuthorizationParser = new BasicAuthorizationParser();

    @CsvSource({
            "Bearer, test@test.com:password",
            "Basic, test@test.com,password"
    })
    @ParameterizedTest
    void 옳바르지_않은_헤더로_인증하면_예외가_발생한다(final String type, final String value) {
        // given
        final String valueEncodedByBase64 = new String(Base64.getEncoder().encode(value.getBytes()));
        final String requestHeader = type + " " + valueEncodedByBase64;

        // expect
        assertThatThrownBy(() -> basicAuthorizationParser.parse(requestHeader))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ValueSource(strings = {"", "  "})
    @ParameterizedTest
    void 빈_헤더로_인증하면_예외가_발생한다(final String header) {
        // expect
        assertThatThrownBy(() -> basicAuthorizationParser.parse(header))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Header 에 인증 정보를 담아주세요.");
    }

    @Test
    void 헤더를_입력받아_유저정보를_반환한다() {
        // given
        final String email = "test@test.com";
        final String password = "password";
        final String authType = "Basic ";
        final String requestAuthHeader = authType + new String(Base64.getUrlEncoder()
                .encode((email + ":" + password).getBytes()));

        // when
        final AuthInfo authInfo = basicAuthorizationParser.parse(requestAuthHeader);

        // then
        assertAll(
                () -> assertThat(authInfo.getEmail()).isEqualTo(email),
                () -> assertThat(authInfo.getPassword()).isEqualTo(password)
        );
    }
}

package cart.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.controller.dto.MemberDto;
import cart.exception.AuthException;
import java.util.Base64;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class BasicAuthorizationExtractorTest {

    private final BasicAuthorizationExtractor basicAuthorizationParser = new BasicAuthorizationExtractor();

    @Test
    @DisplayName("헤더를 입력받아 추출한 값을 반환한다.")
    void extractSuccess() {
        String credential = "gray:hello@hello.com:password";
        String encodedCredential = new String(Base64.getEncoder().encode((credential.getBytes())));
        String header = "Basic " + encodedCredential;

        MemberDto memberDto = basicAuthorizationParser.extract(header);

        assertAll(
                () -> assertThat(memberDto.getEmail()).isEqualTo("hello@hello.com"),
                () -> assertThat(memberDto.getPassword()).isEqualTo("password")
        );
    }

    @Test
    @DisplayName("헤더가 없으면 예외가 발생한다.")
    void extractFailWithNullHeader() {
        String header = null;

        assertThatThrownBy(() -> basicAuthorizationParser.extract(header))
                .isInstanceOf(AuthException.class)
                .hasMessageContaining("인증 정보가 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"Bearer ", "BasicGray ", " "})
    @DisplayName("헤더의 key 값이 basic 이외의 값이면 예외가 발생한다.")
    void extractFailWithWrongHeader(String key) {
        String credential = "gray:hello@hello.com:password";
        String encodedCredential = new String(Base64.getEncoder().encode((credential.getBytes())));
        String header = key + encodedCredential;

        assertThatThrownBy(() -> basicAuthorizationParser.extract(header))
                .isInstanceOf(AuthException.class)
                .hasMessageContaining("올바르지 않은 헤더입니다.");
    }

    @Test
    @DisplayName("헤더의 value 값이 null 이면 예외가 발생한다.")
    void extractFailWithNull() {
        String header = "Basic ";

        assertThatThrownBy(() -> basicAuthorizationParser.extract(header))
                .isInstanceOf(AuthException.class)
                .hasMessageContaining("올바르지 않은 헤더입니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"gray", "gray:hello@hello.com", "hello@hello.com:password"})
    @DisplayName("헤더의 value 값이 형식에 맞지 않으면 예외가 발생한다.")
    void extractFailWithWrongValue() {
        String header = "Basic gray";

        assertThatThrownBy(() -> basicAuthorizationParser.extract(header))
                .isInstanceOf(AuthException.class)
                .hasMessageContaining("올바르지 않은 헤더입니다.");
    }
}

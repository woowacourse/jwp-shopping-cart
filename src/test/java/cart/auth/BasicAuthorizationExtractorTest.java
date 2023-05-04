package cart.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.controller.dto.AuthInfo;
import cart.exception.AuthException;
import java.util.Base64;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BasicAuthorizationExtractorTest {

    private final BasicAuthorizationExtractor basicAuthorizationParser = new BasicAuthorizationExtractor();

    @Test
    @DisplayName("헤더를 입력받아 추출한 값을 반환한다.")
    void extractSuccess() {
        String credential = "gray:hello@hello.com:password";
        String encodedCredential = new String(Base64.getEncoder().encode((credential.getBytes())));
        String header = "Basic " + encodedCredential;

        AuthInfo authInfo = basicAuthorizationParser.extract(header);

        assertAll(
                () -> assertThat(authInfo.getEmail()).isEqualTo("hello@hello.com"),
                () -> assertThat(authInfo.getPassword()).isEqualTo("password")
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

    @Test
    @DisplayName("헤더의 key 값이 basic 이외의 값이면 예외가 발생한다.")
    void extractFailWithWrongHeader() {
        String credential = "gray:hello@hello.com:password";
        String encodedCredential = new String(Base64.getEncoder().encode((credential.getBytes())));
        String header = "Bearer " + encodedCredential;

        assertThatThrownBy(() -> basicAuthorizationParser.extract(header))
                .isInstanceOf(AuthException.class)
                .hasMessageContaining("올바르지 않은 헤더입니다.");
    }

}

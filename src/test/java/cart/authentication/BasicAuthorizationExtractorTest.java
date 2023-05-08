package cart.authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.controller.dto.AuthInfo;
import cart.exception.auth.NotSignInException;
import cart.exception.auth.NotValidTokenFormatException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BasicAuthorizationExtractorTest {

    BasicAuthorizationExtractor extractor = new BasicAuthorizationExtractor();

    @Test
    @DisplayName("올바른 Basic Token이 전달되면 AuthInfo로 변환한다.")
    void extractSuccess() {
        String encoded = encode("a@a.com:a");

        AuthInfo authInfo = extractor.extract("Basic " + encoded);

        assertAll(
                () -> assertThat(authInfo.getEmail()).isEqualTo("a@a.com"),
                () -> assertThat(authInfo.getPassword()).isEqualTo("a")
        );
    }

    @Test
    @DisplayName("Authorization 헤더가 존재하지 않으면 예외가 발생한다.")
    void extractFailWithoutAuthorizationHeader() {
        assertThatThrownBy(() -> extractor.extract(null))
                .isInstanceOf(NotSignInException.class)
                .hasMessage("로그인이 필요한 기능입니다.");
    }

    @Test
    @DisplayName("Basic Token의 구분자가 존재하지 않으면 예외가 발생한다.")
    void extractFailWithoutBasicTokenDelimiter() {
        String encoded = encode("a@a.coma");

        assertThatThrownBy(() -> extractor.extract("Basic " + encoded))
                .isInstanceOf(NotValidTokenFormatException.class)
                .hasMessage("Token 형식이 올바르지 않습니다.");
    }

    @Test
    @DisplayName("Basic Token의 타입 Basic 이 누락되면 예외가 발생한다.")
    void extractFailWithoutBasicType() {
        String encoded = encode("a@a.com:a");

        assertThatThrownBy(() -> extractor.extract(encoded))
                .isInstanceOf(NotValidTokenFormatException.class)
                .hasMessage("Token 형식이 올바르지 않습니다.");
    }

    private String encode(String userInfo) {
        return new String(Base64.getEncoder()
                .encode(userInfo.getBytes(StandardCharsets.UTF_8)));
    }
}

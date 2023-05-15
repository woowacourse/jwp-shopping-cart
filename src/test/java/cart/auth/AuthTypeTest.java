package cart.auth;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import cart.auth.extractor.BasicAuthorizationExtractor;
import cart.exception.AuthorizationException;
import java.util.Base64;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AuthTypeTest {

    public static final String EMAIL = "test@email.com";
    public static final String PASSWORD = "12345678";
    public static final String DELIMITER = ":";
    public static final String credentials = EMAIL + DELIMITER + PASSWORD;
    public static final String BASIC_TYPE = "Basic ";
    public static final String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

    @Test
    @DisplayName("인증 정보에 맞는 extractor를 반환한다.")
    void getExtractor_success() {
        assertThat(AuthType.getExtractor(BASIC_TYPE + encodedCredentials))
                .isInstanceOf(BasicAuthorizationExtractor.class);
    }

    @Test
    @DisplayName("지정된 인증 방식을 사용하지 않으면 예외를 발생시킨다.")
    void getExtractor_fail() {
        assertThatThrownBy(
                () -> AuthType.getExtractor("Joy " + encodedCredentials)
        ).isInstanceOf(AuthorizationException.class)
                .hasMessage("잘못된 인증 방식입니다.");
    }

    @Test
    @DisplayName("인증 정보를 입력하지 않으면 예외가 발생한다.")
    void getExtractor_null() {
        assertThatThrownBy(
                () -> AuthType.getExtractor(null)
        ).isInstanceOf(AuthorizationException.class)
                .hasMessage("사용자 인증 정보가 없습니다.");
    }
}

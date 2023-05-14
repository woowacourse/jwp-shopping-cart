package cart.auth;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import javax.naming.AuthenticationException;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BasicAuthorizationExtractorTest {

    private BasicAuthorizationExtractor extractor;

    @BeforeEach
    void setUp() {
        extractor = new BasicAuthorizationExtractor();
    }

    @DisplayName("헤더가 null일 때 예외처리한다.")
    @Test
    void failExtractWhenNull() {
        String header = null;

        assertThatThrownBy(() -> extractor.extract(header))
                .isInstanceOf(AuthenticationException.class)
                .hasMessage("해당 정보의 유저가 없습니다.");
    }

    @DisplayName("헤더가 Basic으로 시작하지 않을 때 예외처리한다.")
    @Test
    void failExtractWhenNotBasic() {
        String header = "testHeaderNotStartWithBasic";
        assertThatThrownBy(() -> extractor.extract(header))
                .isInstanceOf(AuthenticationException.class)
                .hasMessage("해당 정보의 유저가 없습니다.");
    }

    @DisplayName("정상적인 Basic 인증 정보가 주어졌을 때 통과한다.")
    @Test
    void it_returns_auth_info() throws AuthenticationException {
        String email = "dino@gmail.com";
        String password = "jjongwa96";
        String encodedString = Base64.encodeBase64String((email + ":" + password).getBytes());
        String header = "Basic " + encodedString;

        AuthInfo result = extractor.extract(header);

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            softly.assertThat(result.getEmail()).isEqualTo(email);
            softly.assertThat(result.getPassword()).isEqualTo(password);
        });
    }

}

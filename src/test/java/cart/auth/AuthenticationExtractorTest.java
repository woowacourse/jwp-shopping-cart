package cart.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Base64;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AuthenticationExtractorTest {

    private final AuthenticationExtractor authenticationExtractor = new AuthenticationExtractor();

    @DisplayName("Basic 토큰을 받아 인증 객체를 반환한다")
    @Test
    void extractCredentialTest() {
        String credential = "boxster@email.com:password";
        String encodedCredential = new String(Base64.getEncoder().encode((credential.getBytes())));
        String header = "Basic " + encodedCredential;

        AuthMember result = authenticationExtractor.extractAuthInfo(header);

        assertAll(
                () -> assertThat(result.getEmail()).isEqualTo("boxster@email.com"),
                () -> assertThat(result.getPassword()).isEqualTo("password")
        );
    }

}

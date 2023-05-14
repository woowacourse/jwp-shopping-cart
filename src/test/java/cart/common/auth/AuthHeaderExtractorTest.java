package cart.common.auth;

import cart.exception.AuthException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;

class AuthHeaderExtractorTest {

    AuthHeaderExtractor authHeaderExtractor = new AuthHeaderExtractor();

    @Test
    void 정상적인_인증_헤더() {
        String header = "Basic: " + new String(Base64.getEncoder().encode("cyh6099@gmail.com:qwer1234".getBytes()));
        assertThat(authHeaderExtractor.authenticate(header)).isTrue();
    }

    @Test
    void 인증방식이_Basic이_아니면_예외() {
        String header = "Bearer: " + new String(Base64.getEncoder().encode("cyh6099@gmail.com:qwer1234".getBytes()));
        Assertions.assertThatThrownBy(() -> authHeaderExtractor.authenticate(header))
                .isInstanceOf(AuthException.class);
    }

    @Test
    void 인증헤더에_값이_이메일만_있으면_예외() {
        String header = "Basic: " + new String(Base64.getEncoder().encode("cyh6099@gmail".getBytes()));
        Assertions.assertThatThrownBy(() -> authHeaderExtractor.extract(header))
                .isInstanceOf(AuthException.class);
    }

    @Test
    void 인증헤더에_값이_3개_이상_있으면_예외() {
        String header = "Basic: " + new String(Base64.getEncoder().encode("cyh6099@gmail:qwer:1234".getBytes()));
        Assertions.assertThatThrownBy(() -> authHeaderExtractor.extract(header))
                .isInstanceOf(AuthException.class);
    }
}

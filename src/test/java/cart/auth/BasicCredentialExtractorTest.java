package cart.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.execption.AuthorizationException;
import java.util.Base64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BasicCredentialExtractorTest {

    BasicCredentialExtractor basicCredentialExtractor;
    byte[] encodedInfo;

    @BeforeEach
    void setUp() {
        basicCredentialExtractor = new BasicCredentialExtractor();
        encodedInfo = Base64.getEncoder().encode("email@email:password".getBytes());
    }

    @Test
    void extractMemberInfo() {
        final String encodedString = "Basic " + new String(encodedInfo);
        final Credential memberInfo = basicCredentialExtractor.extractMemberInfo(encodedString);
        assertAll(
                () -> assertThat(memberInfo.getEmail()).isEqualTo("email@email"),
                () -> assertThat(memberInfo.getPassword()).isEqualTo("password")
        );
    }

    @Test
    void extractMemberInfoWithInvalidFormat() {
        String encodedString = "Basicc " + new String(encodedInfo);

        assertThatThrownBy(() -> basicCredentialExtractor.extractMemberInfo(encodedString))
                .isInstanceOf(AuthorizationException.class);
    }
}

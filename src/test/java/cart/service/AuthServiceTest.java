package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.service.dto.MemberInfo;
import cart.execption.AuthorizationException;
import java.util.Base64;
import org.junit.jupiter.api.Test;

class AuthServiceTest {

    @Test
    void extractMemberInfo() {
        AuthService authService = new AuthService();
        final byte[] encodedInfo = Base64.getEncoder().encode("email@email:password".getBytes());
        String encodedString = "Basic " + new String(encodedInfo);
        final MemberInfo memberInfo = authService.extractMemberInfo(encodedString);
        assertAll(
                () -> assertThat(memberInfo.getEmail()).isEqualTo("email@email"),
                () -> assertThat(memberInfo.getPassword()).isEqualTo("password")
        );
    }

    @Test
    void extractMemberInfoWithInvalidFormat() {
        AuthService authService = new AuthService();
        final byte[] encodedInfo = Base64.getEncoder().encode("email@email:password".getBytes());
        String encodedString = "Basicc " + new String(encodedInfo);
        assertThatThrownBy(() -> authService.extractMemberInfo(encodedString))
                .isInstanceOf(AuthorizationException.class);
    }
}

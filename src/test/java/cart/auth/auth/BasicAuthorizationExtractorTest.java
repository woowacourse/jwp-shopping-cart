package cart.auth.auth;

import cart.controller.auth.dto.LoginUser;
import cart.controller.auth.util.BasicAuthorizationExtractor;
import cart.exception.UnauthorizedException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class BasicAuthorizationExtractorTest {

    @DisplayName("Base64로 인코딩된 값을 디코딩해서 LoginUser객체를 반환")
    @Test
    void extract() {
        //given
        String email = "email@email.com";
        String password = "12345678";
        String emailAndPassword = email + ":" + password;
        String value = "Basic " + Base64.encodeBase64String(emailAndPassword.getBytes(StandardCharsets.UTF_8));
        //when
        LoginUser loginUser = BasicAuthorizationExtractor.extract(value);
        //then
        assertAll(
                () -> assertThat(loginUser.getEmail()).isEqualTo(email),
                () -> assertThat(loginUser.getPassword()).isEqualTo(password)
        );
    }

    @DisplayName("basic으로 시작하지 않는 값은 인증 실패 예외")
    @Test
    void extractExceptionWithNotBasic() {
        //given
        String email = "email@email.com";
        String password = "12345678";
        String emailAndPassword = email + ":" + password;
        String value = "Bearer " + Base64.encodeBase64String(emailAndPassword.getBytes(StandardCharsets.UTF_8));
        //then
        assertThatThrownBy(() -> BasicAuthorizationExtractor.extract(value)).isInstanceOf(UnauthorizedException.class);
    }

    @DisplayName("빈 값은 인증 실패 예외")
    @Test
    void extractExceptionWithEmpty() {
        //given
        String value = "Bearer " + Base64.encodeBase64String("".getBytes(StandardCharsets.UTF_8));
        //then
        assertThatThrownBy(() -> BasicAuthorizationExtractor.extract(value)).isInstanceOf(UnauthorizedException.class);
    }
}

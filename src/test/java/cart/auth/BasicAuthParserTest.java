package cart.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.dto.request.AuthRequest;
import cart.exception.custom.UnauthorizedException;
import java.util.Base64;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BasicAuthParserTest {

    private final BasicAuthParser basicAuthParser = new BasicAuthParser();

    @DisplayName("정상적으로 파싱을 성공한다.")
    @Test
    void parse_success() {
        //given
        String givenEmail = "email@naver.com";
        String givenPassword = "password";
        String credential = givenEmail + ":" + givenPassword;
        String encodedCredential = new String(Base64.getEncoder().encode((credential.getBytes())));
        String authorization = "Basic " + encodedCredential;
        //when
        AuthRequest actual = basicAuthParser.parse(authorization);
        //then
        assertAll(
                () -> assertThat(actual.getEmail()).isEqualTo(givenEmail),
                () -> assertThat(actual.getPassword()).isEqualTo(givenPassword)
        );
    }

    @DisplayName("authorization이 null이면 예외를 반환한다.")
    @Test
    void parse_fail_by_null_value() {
        //when && then
        assertThatThrownBy(() -> basicAuthParser.parse(null))
                .isInstanceOf(UnauthorizedException.class)
                .hasMessageContaining("Authorization Header가 존재하지 않습니다.");
    }

    @DisplayName("authorization이 basic으로 시작하지 않는다면 예외를 반환한다.")
    @Test
    void parse_fail_by_wrong_format() {
        //when && then
        assertThatThrownBy(() -> basicAuthParser.parse("anyValue"))
                .isInstanceOf(UnauthorizedException.class)
                .hasMessageContaining("Authorization Header는 'Basic '로 시작해야 합니다.");
    }
}

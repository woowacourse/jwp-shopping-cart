package woowacourse.auth.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @DisplayName("토큰을 샹성한다.")
    @Test
    void createToken() {
        // when & then
        assertThat(authService.createToken(1L)).isNotNull();
    }

    @DisplayName("토큰이 유효한지 확인한다.")
    @Test
    void validateTokenTrue() {
        // given
        String token = authService.createToken(1L);

        // when & then
        assertThat(authService.validateToken(token)).isTrue();
    }

    @DisplayName("토큰이 유효하지 않은지 확인한다.")
    @Test
    void validateTokenFalse() {
        // when & then
        assertThat(authService.validateToken("token")).isFalse();
    }

    @DisplayName("토큰에 담긴 정보를 확인한다.")
    @Test
    void getPayload() {
        // given
        String token = authService.createToken(1L);

        // when & then
        assertThat(authService.getPayload(token)).isEqualTo(String.valueOf(1L));
    }
}

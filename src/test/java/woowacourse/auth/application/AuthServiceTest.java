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
}

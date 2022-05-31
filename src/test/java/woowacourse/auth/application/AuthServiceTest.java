package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import woowacourse.auth.dto.SignInDto;
import woowacourse.auth.dto.TokenResponseDto;
import woowacourse.shoppingcart.dto.CustomerDto;
import woowacourse.shoppingcart.dto.SignUpDto;
import woowacourse.shoppingcart.service.CustomerService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AuthServiceTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AuthService authService;

    @Test
    @DisplayName("로그인 정보를 받아서 토큰을 반환한다.")
    void login() {
        final String customerEmail = "test@test.com";
        final SignUpDto signUpDto = new SignUpDto(customerEmail, "testtest", "test");
        customerService.signUp(signUpDto);
        final SignInDto signInDto = new SignInDto(customerEmail, "testtest");

        final TokenResponseDto token = authService.login(signInDto);
        final String subject = authService.extractPayload(token.getAccessToken());

        assertThat(subject).isEqualTo(customerEmail);
    }

}
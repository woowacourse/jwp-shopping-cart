package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.shoppingcart.Fixtures.CUSTOMER_REQUEST_1;
import static woowacourse.shoppingcart.Fixtures.TOKEN_REQUEST_1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.application.CustomerService;

// TODO: SpringBootTest 대신 적용할 수 있는 방법 공부하기
@SpringBootTest
class AuthServiceTest {
    private final AuthService authService;
    private final CustomerService customerService;

    @Autowired
    public AuthServiceTest(AuthService authService, CustomerService customerService) {
        this.authService = authService;
        this.customerService = customerService;
    }

    @BeforeEach
    void setUp() {
        customerService.create(CUSTOMER_REQUEST_1);
    }

    @DisplayName("올바른 이메일과 패스워드를 전달하면, TokenResponse를 반환한다.")
    @Test
    void generateToken() {
        // when
        TokenResponse tokenResponse = authService.generateToken(TOKEN_REQUEST_1);

        // then
        assertAll(
                () -> assertThat(tokenResponse.getAccessToken()).isNotBlank(),
                () -> assertThat(tokenResponse.getCustomerId()).isPositive()
        );
    }
}

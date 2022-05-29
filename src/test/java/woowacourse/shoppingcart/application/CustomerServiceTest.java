package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.CustomerResponse;
import woowacourse.auth.dto.SignUpRequest;
import woowacourse.auth.dto.SignUpResponse;
import woowacourse.auth.support.JwtTokenProvider;

@SpringBootTest
@Transactional
public class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;

    @Test
    @DisplayName("회원을 저장하고 회원 정보를 반환한다.")
    void signUp() {
        // given
        String name = "greenlawn";
        String email = "green@woowa.com";
        SignUpRequest signUpRequest = new SignUpRequest(name, email, "1234");

        // when
        SignUpResponse signUpResponse = customerService.addCustomer(signUpRequest);

        // then
        assertAll(
                () -> assertThat(signUpResponse.getUsername()).isEqualTo(name),
                () -> assertThat(signUpResponse.getEmail()).isEqualTo(email)
        );
    }

    @Test
    @DisplayName("나의 정보를 반환한다.")
    void findMe() {
        // given
        String name = "greenlawn";
        String email = "green@woowa.com";
        SignUpRequest signUpRequest = new SignUpRequest(name, email, "1234");
        customerService.addCustomer(signUpRequest);

        // when
        CustomerResponse response = customerService.findMe("green@woowa.com");

        // then
        assertThat(response.getUsername()).isEqualTo("greenlawn");
    }
}

package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import woowacourse.auth.dto.SignUpRequest;
import woowacourse.auth.dto.SignUpResponse;

@SpringBootTest
public class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;

    @Test
    @DisplayName("회원을 저장하고 회원 정보를 반환한다.")
    void signUp() {
        // given
        String name = "name";
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
}

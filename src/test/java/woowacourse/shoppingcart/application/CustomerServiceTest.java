package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.shoppingcart.dto.CustomerLoginRequest;
import woowacourse.shoppingcart.dto.CustomerLoginResponse;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @DisplayName("회원가입을 하고 아이디로 회원을 찾는다.")
    @Test
    void signUpAndFindById() {
        // given
        CustomerRequest customerRequest = new CustomerRequest("jojo@naver.com", "jojogreen", "abcde123@");

        // when
        Long id = customerService.signUp(customerRequest);

        // then
        TokenRequest tokenRequest = new TokenRequest(id);
        CustomerResponse customerResponse = customerService.findById(tokenRequest);
        assertAll(
                () -> assertThat(customerResponse.getUserId())
                        .isEqualTo(customerRequest.getUserId()),
                () -> assertThat(customerResponse.getNickname())
                        .isEqualTo(customerRequest.getNickname())
        );
    }

    @DisplayName("username, password 로 로그인한 뒤 회원 정보를 반환한다.")
    @Test
    void login() {
        // given
        CustomerRequest customerRequest = new CustomerRequest("jo@naver.com", "jojogreen", "abcde1234!");
        customerService.signUp(customerRequest);
        CustomerLoginRequest customerLoginRequest = new CustomerLoginRequest("jo@naver.com", "abcde1234!");

        // when
        CustomerLoginResponse customerLoginResponse = customerService.login(customerLoginRequest);

        // then
        assertAll(
                () -> assertThat(customerLoginResponse.getUserId())
                        .isEqualTo(customerRequest.getUserId()),
                () -> assertThat(customerLoginResponse.getNickname())
                        .isEqualTo(customerRequest.getNickname())
        );
    }
}

package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerServiceTest {

    @Autowired
    CustomerService customerService;

    @DisplayName("회원가입을 하고 아이디로 회원을 찾는다.")
    @Test
    void signUpAndFindById() {
        CustomerRequest customerRequest =new CustomerRequest("jojo@naver.com", "jojogreen", "abcde123@");

        Long id = customerService.signUp(customerRequest);

        TokenRequest tokenRequest = new TokenRequest(id);
        CustomerResponse customerResponse = customerService.findById(tokenRequest);
        assertAll(
                () -> assertThat(customerResponse.getUserId())
                        .isEqualTo(customerRequest.getUserId()),
                () -> assertThat(customerResponse.getNickname())
                        .isEqualTo(customerRequest.getNickname())
        );
    }
}

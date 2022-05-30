package woowacourse.shoppingcart.ui;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import woowacourse.shoppingcart.dto.CustomerRegisterRequest;

@SpringBootTest
class CustomerControllerTest {

    @Autowired
    private CustomerController customerController;

    @DisplayName("회원가입을 한다.")
    @Test
    void register() {
        // given 이름, 이메일, 비밀번호를 입력하고
        CustomerRegisterRequest request = new CustomerRegisterRequest("클레이", "djwhy5510@naver.com", "12345678");

        // when 회원 등록을 요청하면
        ResponseEntity<Void> response = customerController.register(request);

        // then 회원이 성공적으로 등록된다.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
}

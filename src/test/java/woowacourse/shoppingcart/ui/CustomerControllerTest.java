package woowacourse.shoppingcart.ui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dto.CustomerRegisterRequest;
import woowacourse.shoppingcart.exception.DuplicatedEmailException;

@SpringBootTest
@Sql("classpath:data.sql")
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

    @DisplayName("회원가입시 동일한 이메일이 존재하는 경우 예외가 발생한다.")
    @Test
    void register_withDuplicatedEmail_throwsException() {
        // given
        CustomerRegisterRequest request = new CustomerRegisterRequest("클레이", "djwhy5510@naver.com", "12345678");
        customerController.register(request);

        // when, then
        assertThatThrownBy(() -> customerController.register(request))
                .isInstanceOf(DuplicatedEmailException.class);
    }
}

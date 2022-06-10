package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.DeleteCustomerRequest;
import woowacourse.shoppingcart.dto.SignUpRequest;
import woowacourse.shoppingcart.dto.SignUpResponse;
import woowacourse.shoppingcart.dto.UpdatePasswordRequest;
import woowacourse.shoppingcart.exception.DuplicateEmailException;
import woowacourse.shoppingcart.exception.DuplicateUsernameException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;
import woowacourse.shoppingcart.exception.InvalidPasswordException;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerDao customerDao;

    @Test
    @DisplayName("회원 가입에 성공한다.")
    void addCustomer() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("greenlawn", "green@woowa.com", "123456q!");

        // when
        SignUpResponse signUpResponse = customerService.addCustomer(signUpRequest);

        // then
        assertAll(
                () -> assertThat(signUpResponse.getUsername()).isEqualTo("greenlawn"),
                () -> assertThat(signUpResponse.getEmail()).isEqualTo("green@woowa.com")
        );
    }

    @Test
    @DisplayName("회원 가입에 실패한다. - 중복된 이름 저장")
    void signUpFail() {
        SignUpRequest signUpRequest = new SignUpRequest("greenlawn", "green@woowa.com", "123456q!");
        customerService.addCustomer(signUpRequest);

        assertThatThrownBy(() -> customerService.addCustomer(signUpRequest))
                .isInstanceOf(DuplicateUsernameException.class)
                .hasMessageContaining("중복된 이름입니다.");
    }

    @Test
    @DisplayName("나의 정보를 반환한다.")
    void findMe() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("greenlawn", "green@woowa.com", "123456q!");
        customerService.addCustomer(signUpRequest);

        // when
        CustomerResponse response = customerService.findMe("greenlawn");

        // then
        assertThat(response.getUsername()).isEqualTo("greenlawn");
    }

    @Test
    @DisplayName("나의 정보를 수정한다.")
    void updateMe() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("greenlawn", "green@woowa.com", "123456q!");
        customerService.addCustomer(signUpRequest);

        // when
        customerService.updateMe("greenlawn", new UpdatePasswordRequest("123456q!", "5678910"));

        // then
        assertThat(customerDao.isValidPasswordByUsername("greenlawn", "5678910")).isTrue();
    }

    @Test
    @DisplayName("비밀번호가 틀리면 나의 정보를 수정할 수 없다.")
    void updateMeThrowException() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("greenlawn", "green@woowa.com", "123456q!");
        customerService.addCustomer(signUpRequest);

        // when & then
        assertThatThrownBy(() ->
                customerService.updateMe("greenlawn", new UpdatePasswordRequest("123578", "125678")))
                .isInstanceOf(InvalidPasswordException.class)
                .hasMessage("비밀번호가 틀렸습니다.");
    }

    @Test
    @DisplayName("회원을 탈퇴한다.")
    void deleteMe() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("greenlawn", "green@woowa.com", "123456q!");
        customerService.addCustomer(signUpRequest);

        // when
        customerService.deleteMe("greenlawn", new DeleteCustomerRequest("123456q!"));

        // given
        assertThatThrownBy(() -> customerDao.findByUsername("greenlawn"))
                .isInstanceOf(InvalidCustomerException.class);
    }

    @Test
    @DisplayName("이메일이 같으면 회원정보를 저장할 수 없다.")
    void addCustomerThrowDuplicateEmailException() {
        // given
        String name1 = "greenlawn";
        String email1 = "green@woowa.com";
        SignUpRequest signUpRequest1 = new SignUpRequest(name1, email1, "123456q!");
        customerService.addCustomer(signUpRequest1);

        // when & then
        String name2 = "rennon";
        String email2 = "green@woowa.com";
        SignUpRequest signUpRequest2 = new SignUpRequest(name2, email2, "123456q!");
        assertThatThrownBy(() -> customerService.addCustomer(signUpRequest2))
                .isInstanceOf(DuplicateEmailException.class)
                .hasMessage("중복된 이메일입니다.");
    }
}

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
@Sql("classpath:schema.sql")
public class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerDao customerDao;

    @Test
    @DisplayName("회원을 저장하고 회원 정보를 반환할 수 있다..")
    void addCustomer() {
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
    @DisplayName("회원 이름이 같으면 회원정보를 저장할 수 없다.")
    void addCustomerThrowDuplicateUsernameException() {
        // given
        String name1 = "greenlawn";
        String email1 = "green@woowa.com";
        SignUpRequest signUpRequest1 = new SignUpRequest(name1, email1, "1234");
        customerService.addCustomer(signUpRequest1);

        // when & then
        String name2 = "greenlawn";
        String email2 = "rennon@woowa.com";
        SignUpRequest signUpRequest2 = new SignUpRequest(name2, email2, "1234");
        assertThatThrownBy(() -> customerService.addCustomer(signUpRequest2))
                .isInstanceOf(DuplicateUsernameException.class)
                .hasMessage("회원이름이 중복되었습니다.");
    }

    @Test
    @DisplayName("이메일이 같으면 회원정보를 저장할 수 없다.")
    void addCustomerThrowDuplicateEmailException() {
        // given
        String name1 = "greenlawn";
        String email1 = "green@woowa.com";
        SignUpRequest signUpRequest1 = new SignUpRequest(name1, email1, "1234");
        customerService.addCustomer(signUpRequest1);

        // when & then
        String name2 = "rennon";
        String email2 = "green@woowa.com";
        SignUpRequest signUpRequest2 = new SignUpRequest(name2, email2, "1234");
        assertThatThrownBy(() -> customerService.addCustomer(signUpRequest2))
                .isInstanceOf(DuplicateEmailException.class)
                .hasMessage("이메일이 중복되었습니다.");
    }

    @Test
    @DisplayName("나의 정보를 반환할 수 있다.")
    void findMe() {
        // given
        String name = "greenlawn";
        String email = "green@woowa.com";
        SignUpRequest signUpRequest = new SignUpRequest(name, email, "1234");
        customerService.addCustomer(signUpRequest);

        // when
        CustomerResponse response = customerService.findMe(name);

        // then
        assertThat(response.getUsername()).isEqualTo("greenlawn");
    }

    @Test
    @DisplayName("나의 정보를 수정할 수 있다.")
    void updateMe() {
        // given
        String name = "greenlawn";
        String email = "green@woowa.com";
        SignUpRequest signUpRequest = new SignUpRequest(name, email, "1234");
        customerService.addCustomer(signUpRequest);

        // when
        customerService.updateMe(name, new UpdatePasswordRequest("1234", "5678"));

        // then
        assertThat(customerDao.isValidPasswordByUsername(name, "5678")).isTrue();
    }

    @Test
    @DisplayName("비밀번호가 틀리면 나의 정보를 수정할 수 없다.")
    void updateMeThrowException() {
        // given
        String name = "greenlawn";
        String email = "green@woowa.com";
        SignUpRequest signUpRequest = new SignUpRequest(name, email, "1234");
        customerService.addCustomer(signUpRequest);

        // when & then
        assertThatThrownBy(() ->
                customerService.updateMe(name, new UpdatePasswordRequest("1235", "5678")))
                .isInstanceOf(InvalidPasswordException.class)
                .hasMessage("비밀번호가 틀렸습니다.");
    }

    @Test
    @DisplayName("회원을 탈퇴할 수 있다.")
    void deleteMe() {
        // given
        String name = "greenlawn";
        String email = "green@woowa.com";
        SignUpRequest signUpRequest = new SignUpRequest(name, email, "1234");
        customerService.addCustomer(signUpRequest);

        // when
        customerService.deleteMe(name, new DeleteCustomerRequest("1234"));

        // then
        assertThatThrownBy(() -> customerDao.findByUsername(name))
                .isInstanceOf(InvalidCustomerException.class);
    }

    @Test
    @DisplayName("비밀번호가 틀리면 회원을 탈퇴할 수 없다.")
    void deleteMeThrowException() {
        // given
        String name = "greenlawn";
        String email = "green@woowa.com";
        SignUpRequest signUpRequest = new SignUpRequest(name, email, "1234");
        customerService.addCustomer(signUpRequest);

        // when & then
        assertThatThrownBy(() -> customerService.deleteMe(name, new DeleteCustomerRequest("1235")))
                .isInstanceOf(InvalidPasswordException.class)
                .hasMessage("비밀번호가 틀렸습니다.");

    }
}

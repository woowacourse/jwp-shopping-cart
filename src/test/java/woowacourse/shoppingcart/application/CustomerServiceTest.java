package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Username;
import woowacourse.shoppingcart.dto.request.DeleteCustomerRequest;
import woowacourse.shoppingcart.dto.request.SignUpRequest;
import woowacourse.shoppingcart.dto.request.UpdatePasswordRequest;
import woowacourse.shoppingcart.dto.response.CustomerResponse;
import woowacourse.shoppingcart.dto.response.SignUpResponse;
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
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원을 저장하고 회원 정보를 반환할 수 있다.")
    void addCustomer() {
        // given
        String name = "greenlawn";
        String email = "green@woowa.com";
        SignUpRequest signUpRequest = new SignUpRequest(name, email, "123456");

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
        SignUpRequest signUpRequest1 = new SignUpRequest(name1, email1, "123456");
        customerService.addCustomer(signUpRequest1);

        // when & then
        String name2 = "greenlawn";
        String email2 = "rennon@woowa.com";
        SignUpRequest signUpRequest2 = new SignUpRequest(name2, email2, "123456");
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
        SignUpRequest signUpRequest1 = new SignUpRequest(name1, email1, "123456");
        customerService.addCustomer(signUpRequest1);

        // when & then
        String name2 = "rennon";
        String email2 = "green@woowa.com";
        SignUpRequest signUpRequest2 = new SignUpRequest(name2, email2, "123456");
        assertThatThrownBy(() -> customerService.addCustomer(signUpRequest2))
                .isInstanceOf(DuplicateEmailException.class)
                .hasMessage("이메일이 중복되었습니다.");
    }

    @Test
    @DisplayName("회원의 정보를 반환할 수 있다.")
    void findCustomer() {
        // given
        String name = "greenlawn";
        String email = "green@woowa.com";
        SignUpRequest signUpRequest = new SignUpRequest(name, email, "123456");
        customerService.addCustomer(signUpRequest);

        // when
        CustomerResponse response = customerService.findCustomer(name);

        // then
        assertThat(response.getUsername()).isEqualTo("greenlawn");
    }

    @Test
    @DisplayName("회원의 정보를 수정할 수 있다.")
    void updateCustomer() {
        // given
        String name = "greenlawn";
        String email = "green@woowa.com";
        SignUpRequest signUpRequest = new SignUpRequest(name, email, "123456");
        customerService.addCustomer(signUpRequest);

        // when
        customerService.updateCustomer(name, new UpdatePasswordRequest("123456", "567890"));
        Customer customer = customerDao.findByUsername(new Username(name));

        // then
        assertThat(passwordEncoder.matches("567890", customer.getPassword().getValue())).isTrue();
    }

    @Test
    @DisplayName("비밀번호가 틀리면 회원의 정보를 수정할 수 없다.")
    void updateCustomerThrowException() {
        // given
        String name = "greenlawn";
        String email = "green@woowa.com";
        SignUpRequest signUpRequest = new SignUpRequest(name, email, "123456");
        customerService.addCustomer(signUpRequest);

        // when & then
        assertThatThrownBy(() ->
                customerService.updateCustomer(name, new UpdatePasswordRequest("123567", "567890")))
                .isInstanceOf(InvalidPasswordException.class)
                .hasMessage("비밀번호가 틀렸습니다.");
    }

    @Test
    @DisplayName("회원을 탈퇴할 수 있다.")
    void deleteCustomer() {
        // given
        String name = "greenlawn";
        String email = "green@woowa.com";
        SignUpRequest signUpRequest = new SignUpRequest(name, email, "123456");
        customerService.addCustomer(signUpRequest);

        // when
        customerService.deleteCustomer(name, new DeleteCustomerRequest("123456"));

        // then
        assertThatThrownBy(() -> customerDao.findByUsername(new Username(name)))
                .isInstanceOf(InvalidCustomerException.class);
    }

    @Test
    @DisplayName("비밀번호가 틀리면 회원을 탈퇴할 수 없다.")
    void deleteCustomerThrowException() {
        // given
        String name = "greenlawn";
        String email = "green@woowa.com";
        SignUpRequest signUpRequest = new SignUpRequest(name, email, "123456");
        customerService.addCustomer(signUpRequest);

        // when & then
        assertThatThrownBy(() -> customerService.deleteCustomer(name, new DeleteCustomerRequest("123567")))
                .isInstanceOf(InvalidPasswordException.class)
                .hasMessage("비밀번호가 틀렸습니다.");
    }
}

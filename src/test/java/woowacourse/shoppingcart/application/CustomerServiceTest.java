package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.auth.exception.AuthException;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.customer.CustomerProfileRequest;
import woowacourse.shoppingcart.dto.customer.CustomerRequest;
import woowacourse.shoppingcart.dto.customer.PasswordRequest;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
class CustomerServiceTest {

    private final CustomerService customerService;

    @Autowired
    public CustomerServiceTest(CustomerService customerService) {
        this.customerService = customerService;
    }

    @DisplayName("이메일이 중복되는지 확인한다.")
    @ParameterizedTest
    @CsvSource(value = {"email@email.com, false", "distinctemail@email.com, true"})
    void isDistinctEmail(final String email, final Boolean expected) {
        assertThat(customerService.isDistinctEmail(email)).isEqualTo(expected);
    }

    @DisplayName("회원가입을 진행한다.")
    @Test
    void signUp() {
        String email = "newemail@email.com";
        String nickname = "쿼리치";
        String password = "password123!";

        CustomerRequest customerRequest = new CustomerRequest(email, nickname, password);
        Customer customer = customerService.signUp(customerRequest);

        assertThat(customer).isEqualTo(new Customer(email, nickname, password));
    }

    @DisplayName("비밀번호가 일치하는지 확인한다.")
    @Test
    void checkPassword() {
        String email = "email@email.com";
        PasswordRequest passwordRequest = new PasswordRequest("password123!");

        assertDoesNotThrow(() -> customerService.checkPassword(email, passwordRequest));
    }

    @DisplayName("비밀번호가 일치하지 않으면 예외가 발생한다.")
    @Test
    void checkInvalidPassword() {
        String email = "email@email.com";
        PasswordRequest passwordRequest = new PasswordRequest("password486!");

        assertThatThrownBy(() -> customerService.checkPassword(email, passwordRequest))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessage("비밀번호가 일치하지 않습니다.");
    }

    @DisplayName("이메일로 회원정보를 찾는다.")
    @Test
    void findByEmail() {
        String email = "email@email.com";
        Customer customer = customerService.findByEmail(email);

        assertThat(customer).isEqualTo(new Customer(email, "파랑", "password123!"));
    }

    @DisplayName("회원정보를 수정한다.")
    @Test
    void updateProfile() {
        final String email = "email@email.com";
        final CustomerProfileRequest customerProfileRequest = new CustomerProfileRequest("파리채");

        assertDoesNotThrow(() -> customerService.updateProfile(email, customerProfileRequest));
    }

    @DisplayName("회원 정보 수정 시 존재하지 않는 이메일이 들어온 경우 예외가 발생한다.")
    @Test
    void updateProfileFail() {
        final String email = "notexistingemail@email.com";
        final CustomerProfileRequest customerProfileRequest = new CustomerProfileRequest("파리채");

        assertThatThrownBy(() -> customerService.updateProfile(email, customerProfileRequest))
                .isInstanceOf(AuthException.class)
                .hasMessage("유효하지 않은 인증입니다.");
    }

    @DisplayName("비밀번호를 수정한다.")
    @Test
    void updatePassword() {
        final String email = "email@email.com";
        final PasswordRequest passwordRequest = new PasswordRequest("newpassword123!");

        assertDoesNotThrow(() -> customerService.updatePassword(email, passwordRequest));
    }

    @DisplayName("비밀번호 수정 시 존재하지 않는 이메일이 들어온 경우 예외가 발생한다.")
    @Test
    void updatePasswordFail() {
        final String email = "notexistingemail@email.com";
        final PasswordRequest passwordRequest = new PasswordRequest("newpassword123!");

        assertThatThrownBy(() -> customerService.updatePassword(email, passwordRequest))
                .isInstanceOf(AuthException.class)
                .hasMessage("유효하지 않은 인증입니다.");
    }

    @DisplayName("회원을 삭제한다.")
    @Test
    void delete() {
        final String email = "email@email.com";

        assertDoesNotThrow(() -> customerService.delete(email));
    }

    @DisplayName("회원 삭제 시 존재하지 않는 이메일이 들어온 경우 예외가 발생한다.")
    @Test
    void deleteFail() {
        final String email = "notexistingemail@email.com";

        assertThatThrownBy(() -> customerService.delete(email))
                .isInstanceOf(AuthException.class)
                .hasMessage("유효하지 않은 인증입니다.");
    }
}

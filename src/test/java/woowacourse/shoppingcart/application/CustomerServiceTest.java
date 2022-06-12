package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.auth.exception.AuthException;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.Email;
import woowacourse.shoppingcart.domain.customer.Nickname;
import woowacourse.shoppingcart.domain.customer.Password;
import woowacourse.shoppingcart.dto.customer.CustomerProfileRequest;
import woowacourse.shoppingcart.dto.customer.CustomerRequest;
import woowacourse.shoppingcart.dto.customer.PasswordRequest;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class CustomerServiceTest {

    public static final Email EMAIL = new Email("email@email.com");
    public static final Email NOT_EXISTING_EMAIL = new Email("notexistingemail@email.com");

    private final CustomerService customerService;

    public CustomerServiceTest(CustomerService customerService) {
        this.customerService = customerService;
    }

    @DisplayName("이메일이 중복되는지 확인한다.")
    @ParameterizedTest
    @CsvSource(value = {"email@email.com, false", "distinctemail@email.com, true"})
    void isUniqueEmail(final String email, final Boolean expected) {
        assertThat(customerService.isUniqueEmail(new Email(email))).isEqualTo(expected);
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
        PasswordRequest passwordRequest = new PasswordRequest("password123!");

        assertDoesNotThrow(() -> customerService.checkPassword(EMAIL, passwordRequest));
    }

    @DisplayName("비밀번호가 일치하지 않으면 예외가 발생한다.")
    @Test
    void checkInvalidPassword() {
        PasswordRequest passwordRequest = new PasswordRequest("password486!");

        assertThatThrownBy(() -> customerService.checkPassword(EMAIL, passwordRequest))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessage("비밀번호가 일치하지 않습니다.");
    }

    @DisplayName("이메일로 회원정보를 찾는다.")
    @Test
    void findByEmail() {
        Customer customer = customerService.findByEmail(EMAIL);

        assertThat(customer)
                .isEqualTo(new Customer(EMAIL, new Nickname("파랑"), new Password("password123!")));
    }

    @DisplayName("회원정보를 수정한다.")
    @Test
    void updateProfile() {
        final CustomerProfileRequest customerProfileRequest = new CustomerProfileRequest("파리채");

        assertDoesNotThrow(() -> customerService.updateProfile(EMAIL, customerProfileRequest));
    }

    @DisplayName("회원 정보 수정 시 존재하지 않는 이메일이 들어온 경우 예외가 발생한다.")
    @Test
    void updateProfileFail() {
        final CustomerProfileRequest customerProfileRequest = new CustomerProfileRequest("파리채");

        assertThatThrownBy(() -> customerService.updateProfile(NOT_EXISTING_EMAIL, customerProfileRequest))
                .isInstanceOf(AuthException.class)
                .hasMessage("유효하지 않은 인증입니다.");
    }

    @DisplayName("비밀번호를 수정한다.")
    @Test
    void updatePassword() {
        final PasswordRequest passwordRequest = new PasswordRequest("newpassword123!");

        assertDoesNotThrow(() -> customerService.updatePassword(EMAIL, passwordRequest));
    }

    @DisplayName("비밀번호 수정 시 존재하지 않는 이메일이 들어온 경우 예외가 발생한다.")
    @Test
    void updatePasswordFail() {
        final PasswordRequest passwordRequest = new PasswordRequest("newpassword123!");

        assertThatThrownBy(() -> customerService.updatePassword(NOT_EXISTING_EMAIL, passwordRequest))
                .isInstanceOf(AuthException.class)
                .hasMessage("유효하지 않은 인증입니다.");
    }

    @DisplayName("회원을 삭제한다.")
    @Test
    void delete() {
        assertDoesNotThrow(() -> customerService.delete(EMAIL));
    }

    @DisplayName("회원 삭제 시 존재하지 않는 이메일이 들어온 경우 예외가 발생한다.")
    @Test
    void deleteFail() {
        assertThatThrownBy(() -> customerService.delete(NOT_EXISTING_EMAIL))
                .isInstanceOf(AuthException.class)
                .hasMessage("유효하지 않은 인증입니다.");
    }
}

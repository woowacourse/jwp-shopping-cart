package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.DeleteCustomerRequest;
import woowacourse.shoppingcart.dto.SignUpRequest;
import woowacourse.shoppingcart.dto.SignUpResponse;
import woowacourse.shoppingcart.dto.UpdatePasswordRequest;
import woowacourse.shoppingcart.exception.DuplicateUsernameException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;
import woowacourse.shoppingcart.exception.InvalidPasswordException;

@SpringBootTest
@Transactional
public class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerDao customerDao;

    @Test
    @DisplayName("회원 가입에 성공한다.")
    void addCustomer() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("greenlawn", "green@woowa.com", "1234");

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
        SignUpRequest signUpRequest = new SignUpRequest("greenlawn", "green@woowa.com", "1234");
        customerService.addCustomer(signUpRequest);

        assertThatThrownBy(() -> customerService.addCustomer(signUpRequest))
                .isInstanceOf(DuplicateUsernameException.class)
                .hasMessageContaining("중복된 이름입니다.");
    }

    @Test
    @DisplayName("나의 정보를 반환한다.")
    void findMe() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("greenlawn", "green@woowa.com", "1234");
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
        SignUpRequest signUpRequest = new SignUpRequest("greenlawn", "green@woowa.com", "1234");
        customerService.addCustomer(signUpRequest);

        // when
        customerService.updateMe("greenlawn", new UpdatePasswordRequest("1234", "5678"));

        // then
        assertThat(customerDao.isValidPasswordByUsername("greenlawn", "5678")).isTrue();
    }

    @Test
    @DisplayName("비밀번호가 틀리면 나의 정보를 수정할 수 없다.")
    void updateMeThrowException() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("greenlawn", "green@woowa.com", "1234");
        customerService.addCustomer(signUpRequest);

        // when & then
        assertThatThrownBy(() ->
                customerService.updateMe("greenlawn", new UpdatePasswordRequest("1235", "5678")))
                .isInstanceOf(InvalidPasswordException.class)
                .hasMessage("비밀번호가 틀렸습니다.");
    }

    @Test
    @DisplayName("회원을 탈퇴한다.")
    void deleteMe() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("greenlawn", "green@woowa.com", "1234");
        customerService.addCustomer(signUpRequest);

        // when
        customerService.deleteMe("greenlawn", new DeleteCustomerRequest("1234"));

        // given
        assertThatThrownBy(() -> customerDao.findByUsername("greenlawn"))
                .isInstanceOf(InvalidCustomerException.class);
    }
}

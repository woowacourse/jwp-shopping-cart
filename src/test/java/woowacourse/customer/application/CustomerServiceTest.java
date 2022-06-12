package woowacourse.customer.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.auth.exception.InvalidLoginException;
import woowacourse.customer.dto.CustomerResponse;
import woowacourse.customer.dto.SignupRequest;
import woowacourse.customer.dto.UpdateCustomerRequest;
import woowacourse.customer.dto.UpdatePasswordRequest;
import woowacourse.customer.exception.InvalidCustomerException;

@Transactional
@SpringBootTest
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    private final String username = "username";
    private final String password = "password";
    private final String phoneNumber = "01011112222";
    private final String address = "서울시 여러분";
    private final SignupRequest signupRequest = new SignupRequest(username, password, phoneNumber, address);

    @BeforeEach
    void setUp() {
        customerService.deleteCustomerByUsername(username);
    }

    @DisplayName("회원을 저장한다.")
    @Test
    void saveCustomer() {
        assertDoesNotThrow(() -> customerService.save(signupRequest));
    }

    @DisplayName("회원 저장 시 username이 이미 존재하면 예외를 반환한다.")
    @Test
    void saveDuplicateUsername() {
        // given
        customerService.save(signupRequest);

        // then
        assertThatThrownBy(() -> customerService.save(signupRequest))
            .isInstanceOf(InvalidCustomerException.class)
            .hasMessage("이미 존재하는 사용자 이름입니다.");
    }

    @DisplayName("이름으로 회원을 조회한다.")
    @Test
    void findByUsername() {
        // given
        customerService.save(signupRequest);

        // when
        final CustomerResponse findCustomerResponse = customerService.findCustomerByUsername(username);

        // given
        assertAll(
            () -> assertThat(findCustomerResponse.getUsername()).isEqualTo(signupRequest.getUsername()),
            () -> assertThat(findCustomerResponse.getPhoneNumber()).isEqualTo(signupRequest.getPhoneNumber()),
            () -> assertThat(findCustomerResponse.getAddress()).isEqualTo(signupRequest.getAddress())
        );
    }

    @DisplayName("비밀번호가 일치하는지 확인한다.")
    @Test
    void confirmPassword() {
        customerService.save(signupRequest);
        final CustomerResponse customerResponse = customerService.findCustomerByUsername(username);

        assertDoesNotThrow(() -> customerService.confirmPassword(customerResponse.getUsername(), password));
    }

    @DisplayName("phoneNumber와 address를 수정한다.")
    @Test
    void updateCustomer() {
        // given
        customerService.save(signupRequest);

        final UpdateCustomerRequest updateCustomerRequest = new UpdateCustomerRequest("01012123434", "서울시 여러분");
        customerService.updateInfo(username, updateCustomerRequest);

        final CustomerResponse customerResponse = customerService.findCustomerByUsername(username);

        assertAll(
            () -> assertThat(customerResponse.getPhoneNumber()).isEqualTo(updateCustomerRequest.getPhoneNumber()),
            () -> assertThat(customerResponse.getAddress()).isEqualTo(updateCustomerRequest.getAddress())
        );
    }

    @DisplayName("password를 수정한다.")
    @Test
    void updatePassword() {
        // given
        customerService.save(signupRequest);

        final UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest("ehdgh1111");

        assertDoesNotThrow(() -> customerService.updatePassword(username, updatePasswordRequest));
    }

    @DisplayName("회원을 삭제한다.")
    @Test
    void delete() {
        // given
        customerService.save(signupRequest);

        // when
        customerService.deleteCustomerByUsername(username);

        // then
        assertThatThrownBy(() -> customerService.findCustomerByUsername(username))
            .isInstanceOf(InvalidLoginException.class);
    }
}

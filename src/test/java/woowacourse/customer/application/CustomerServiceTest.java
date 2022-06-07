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

import woowacourse.customer.domain.Customer;
import woowacourse.customer.dto.SignupRequest;
import woowacourse.customer.dto.UpdateCustomerRequest;
import woowacourse.customer.dto.UpdatePasswordRequest;
import woowacourse.customer.support.passwordencoder.PasswordEncoder;
import woowacourse.shoppingcart.exception.InvalidCustomerException;
import woowacourse.shoppingcart.exception.UserNotFoundException;

@Transactional
@SpringBootTest
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final String username = "username";
    private final String password = "password";
    private final String phoneNumber = "01011112222";
    private final String address = "서울시 여러분";
    private final SignupRequest signupRequest = new SignupRequest(username, password, phoneNumber, address);

    @BeforeEach
    void setUp() {
        customerService.deleteByUsername(username);
    }

    @DisplayName("회원을 저장한다.")
    @Test
    void saveCustomer() {
        // when
        final Customer customer = customerService.save(signupRequest);

        // then
        assertAll(
            () -> assertThat(customer.getId()).isNotNull(),
            () -> assertThat(customer.getUsername().getValue()).isEqualTo(signupRequest.getUsername()),
            () -> assertDoesNotThrow(() -> customer.getPassword().matches(passwordEncoder, password)),
            () -> assertThat(customer.getPhoneNumber().getValue()).isEqualTo(signupRequest.getPhoneNumber()),
            () -> assertThat(customer.getAddress()).isEqualTo(signupRequest.getAddress())
        );
    }

    @DisplayName("회원 저장 시 username이 이미 존재하면 예외를 반환한다.")
    @Test
    void saveDuplicateUsername() {
        // given
        customerService.save(signupRequest);

        // then
        assertThatThrownBy(() -> customerService.save(signupRequest))
            .isInstanceOf(InvalidCustomerException.class)
            .hasMessage("이미 존재하는 username입니다.");
    }

    @DisplayName("이름으로 회원을 조회한다.")
    @Test
    void findByUsername() {
        // given
        final Customer savedCustomer = customerService.save(signupRequest);

        // when
        final Customer findCustomer = customerService.findByUsername(username);

        // given
        assertAll(
            () -> assertThat(findCustomer.getId()).isNotNull(),
            () -> assertThat(findCustomer.getUsername()).isEqualTo(savedCustomer.getUsername()),
            () -> assertThat(findCustomer.getPassword()).isEqualTo(savedCustomer.getPassword()),
            () -> assertThat(findCustomer.getPhoneNumber()).isEqualTo(savedCustomer.getPhoneNumber()),
            () -> assertThat(findCustomer.getAddress()).isEqualTo(savedCustomer.getAddress())
        );
    }

    @DisplayName("비밀번호가 일치하는지 확인한다.")
    @Test
    void confirmPassword() {
        final Customer savedCustomer = customerService.save(signupRequest);

        assertDoesNotThrow(() -> customerService.confirmPassword(savedCustomer.getUsername().getValue(), password));
    }

    @DisplayName("phoneNumber와 address를 수정한다.")
    @Test
    void updateCustomer() {
        // given
        customerService.save(signupRequest);

        final UpdateCustomerRequest updateCustomerRequest = new UpdateCustomerRequest("01012123434", "서울시 여러분");
        customerService.updateInfo(username, updateCustomerRequest);

        final Customer customer = customerService.findByUsername(username);

        assertAll(
            () -> assertThat(customer.getPhoneNumber().getValue()).isEqualTo(updateCustomerRequest.getPhoneNumber()),
            () -> assertThat(customer.getAddress()).isEqualTo(updateCustomerRequest.getAddress())
        );
    }

    @DisplayName("password를 수정한다.")
    @Test
    void updatePassword() {
        // given
        customerService.save(signupRequest);

        final UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest("ehdgh1111");
        customerService.updatePassword(username, updatePasswordRequest);

        final Customer customer = customerService.findByUsername(username);

        assertDoesNotThrow(() -> customer.getPassword().matches(passwordEncoder, "ehdgh1111"));
    }

    @DisplayName("회원을 삭제한다.")
    @Test
    void delete() {
        // given
        customerService.save(signupRequest);

        // when
        customerService.deleteByUsername(username);

        // then
        assertThatThrownBy(() -> customerService.findByUsername(username))
            .isInstanceOf(UserNotFoundException.class);
    }
}

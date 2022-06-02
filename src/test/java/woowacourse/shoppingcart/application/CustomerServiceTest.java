package woowacourse.shoppingcart.application;

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

import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.SignupRequest;
import woowacourse.shoppingcart.dto.UpdateCustomerRequest;
import woowacourse.shoppingcart.exception.EmptyResultException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;
import woowacourse.shoppingcart.support.passwordencoder.PasswordEncoder;

@Transactional
@SpringBootTest
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        customerService.deleteByUsername("dongho108");
    }

    @DisplayName("회원을 저장한다.")
    @Test
    void saveCustomer() {
        // given
        final SignupRequest signupRequest = new SignupRequest("dongho108", "ehdgh1234", "01022223333", "인천 서구 검단로");

        // when
        final Customer customer = customerService.save(signupRequest);

        // then
        assertAll(
            () -> assertThat(customer.getId()).isNotNull(),
            () -> assertThat(customer.getUsername().getValue()).isEqualTo(signupRequest.getUsername()),
            () -> assertDoesNotThrow(() -> customer.getPassword().matches(passwordEncoder, "ehdgh1234")),
            () -> assertThat(customer.getPhoneNumber().getValue()).isEqualTo(signupRequest.getPhoneNumber()),
            () -> assertThat(customer.getAddress()).isEqualTo(signupRequest.getAddress())
        );
    }

    @DisplayName("회원 저장 시 username이 이미 존재하면 예외를 반환한다.")
    @Test
    void saveDuplicateUsername() {
        // given
        final SignupRequest signupRequest = new SignupRequest("jjang9", "password12", "01001012121", "서울시");
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
        SignupRequest signupRequest = new SignupRequest("dongho108", "ehdgh1234", "01022728572", "인천 서구 검단로");
        Customer savedCustomer = customerService.save(signupRequest);

        // when
        Customer findCustomer = customerService.findByUsername(signupRequest.getUsername());

        // given
        assertAll(
            () -> assertThat(findCustomer.getId()).isNotNull(),
            () -> assertThat(findCustomer.getUsername()).isEqualTo(savedCustomer.getUsername()),
            () -> assertThat(findCustomer.getPassword()).isEqualTo(savedCustomer.getPassword()),
            () -> assertThat(findCustomer.getPhoneNumber()).isEqualTo(savedCustomer.getPhoneNumber()),
            () -> assertThat(findCustomer.getAddress()).isEqualTo(savedCustomer.getAddress())
        );
    }

    @DisplayName("phoneNumber와 address를 수정한다.")
    @Test
    void updateCustomer() {
        // given
        SignupRequest signupRequest = new SignupRequest("dongho108", "ehdgh1234", "01022728572", "인천 서구 검단로");
        customerService.save(signupRequest);

        UpdateCustomerRequest updateCustomerRequest = new UpdateCustomerRequest("01012123434", "서울시 여러분");
        customerService.updateInfo("dongho108", updateCustomerRequest);

        Customer customer = customerService.findByUsername("dongho108");

        assertAll(
            () -> assertThat(customer.getPhoneNumber().getValue()).isEqualTo(updateCustomerRequest.getPhoneNumber()),
            () -> assertThat(customer.getAddress()).isEqualTo(updateCustomerRequest.getAddress())
        );
    }

    @DisplayName("password를 수정한다.")
    @Test
    void updatePassword() {
        // given
        SignupRequest signupRequest = new SignupRequest("dongho108", "ehdgh1234", "01022728572", "인천 서구 검단로");
        customerService.save(signupRequest);

        UpdateCustomerRequest updateCustomerRequest = new UpdateCustomerRequest("ehdgh1111");
        customerService.updatePassword("dongho108", updateCustomerRequest);

        Customer customer = customerService.findByUsername("dongho108");

        assertDoesNotThrow(() -> customer.getPassword().matches(passwordEncoder, "ehdgh1111"));
    }

    @DisplayName("회원을 삭제한다.")
    @Test
    void delete() {
        // given
        String username = "dongho108";
        SignupRequest signupRequest = new SignupRequest(username, "ehdgh1234", "01022728572", "인천 서구 검단로");
        customerService.save(signupRequest);

        // when
        customerService.deleteByUsername(username);

        // then
        assertThatThrownBy(() -> customerService.findByUsername(username))
            .isInstanceOf(EmptyResultException.class);
    }
}

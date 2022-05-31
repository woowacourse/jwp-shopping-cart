package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.shoppingcart.dto.UpdateCustomerRequest;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.SignupRequest;

@SpringBootTest
@Transactional
class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;

    @DisplayName("회원을 저장한다.")
    @Test
    void saveCustomer() {
        // given
        SignupRequest signupRequest = new SignupRequest("dongho108", "ehdgh1234", "01022728572", "인천 서구 검단로");

        // when
        Customer customer = customerService.save(signupRequest);

        // then
        assertAll(
            () -> assertThat(customer.getId()).isNotNull(),
            () -> assertThat(customer.getUsername().getValue()).isEqualTo(signupRequest.getUsername()),
            () -> assertThat(customer.getPassword().getValue()).isEqualTo(signupRequest.getPassword()),
            () -> assertThat(customer.getPhoneNumber().getValue()).isEqualTo(signupRequest.getPhoneNumber()),
            () -> assertThat(customer.getAddress()).isEqualTo(signupRequest.getAddress())
        );
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

        assertThat(customer.getPassword().getValue()).isEqualTo(updateCustomerRequest.getPassword());
    }
}

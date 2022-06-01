package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.exception.DuplicateCustomerException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@SpringBootTest
@Transactional
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @DisplayName("정상적으로 회원 등록")
    @Test
    void addCustomer() {
        CustomerRequest request = new CustomerRequest("tonic@email.com", "12345678a", "토닉");
        customerService.registCustomer(request);
        Customer customer = customerService.findByEmail("tonic@email.com");
        assertAll(
                () -> assertThat(customer.getEmail()).isEqualTo("tonic@email.com"),
                () -> assertThat(customer.getPassword()).isEqualTo("12345678a"),
                () -> assertThat(customer.getNickname()).isEqualTo("토닉"),
                () -> assertThat(customer.getId()).isNotNull()
        );
    }

    @DisplayName("중복된 email로 회원 등록")
    @Test
    void duplicatedEmailCustomer() {
        CustomerRequest request = new CustomerRequest("tonic@email.com", "12345678a", "토닉");
        customerService.registCustomer(request);

        assertThatThrownBy(() -> customerService.registCustomer(request))
                .isInstanceOf(DuplicateCustomerException.class);
    }

    @DisplayName("email로 회원 조회")
    @Test
    void findByEmail() {
        CustomerRequest request = new CustomerRequest("tonic@email.com", "12345678a", "토닉");
        customerService.registCustomer(request);

        Customer customer = customerService.findByEmail("tonic@email.com");

        assertAll(
                () -> assertThat(customer.getEmail()).isEqualTo("tonic@email.com"),
                () -> assertThat(customer.getNickname()).isEqualTo("토닉"),
                () -> assertThat(customer.getPassword()).isEqualTo("12345678a")
        );
    }

    @DisplayName("가입하지 않은 email로 회원 조회 시 예외 발생")
    @Test
    void notFoundCustomerByEmailThrowException() {
        assertThatThrownBy(() -> customerService.findByEmail("tonic@email.com"))
                .isInstanceOf(InvalidCustomerException.class);
    }
}

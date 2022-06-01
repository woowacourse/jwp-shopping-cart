package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerUpdateRequest;
import woowacourse.shoppingcart.exception.DuplicateCustomerException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@SpringBootTest
@Transactional
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerDao customerDao;

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

    @DisplayName("존재하지 않는 이메일로 탈퇴 시 예외 발생")
    @Test
    void deleteByNotExistEmail() {
        assertThatThrownBy(() -> customerService.deleteByEmail("notExists@email.com"))
                .isInstanceOf(InvalidCustomerException.class);
    }

    @DisplayName("이메일로 회원 탈퇴")
    @Test
    void deleteByEmail() {
        CustomerRequest request = new CustomerRequest("tonic@email.com", "12345678a", "토닉");
        customerService.registCustomer(request);

        customerService.deleteByEmail("tonic@email.com");

        assertThat(customerDao.existByEmail("tonic@email.com")).isFalse();
    }

    @DisplayName("존재하지 않는 이메일로 수정 시 예외 발생")
    @Test
    void updateByNotExistEmail() {
        assertThatThrownBy(() -> customerService.updateCustomer("tonic@email.com", new CustomerUpdateRequest("tonic", "12345678a")))
                .isInstanceOf(InvalidCustomerException.class);
    }

    @DisplayName("정상적인 회원 정보 수정")
    @Test
    void updateCustomer() {
        CustomerRequest request = new CustomerRequest("tonic@email.com", "12345678a", "토닉");
        customerService.registCustomer(request);

        String newNickname = "토닉2";
        String newPassword = "newPassword1";
        customerService.updateCustomer("tonic@email.com", new CustomerUpdateRequest(newNickname, newPassword));
        Customer customer = customerService.findByEmail("tonic@email.com");

        assertThat(customer.getPassword()).isEqualTo(newPassword);
        assertThat(customer.getNickname()).isEqualTo(newNickname);
    }
}

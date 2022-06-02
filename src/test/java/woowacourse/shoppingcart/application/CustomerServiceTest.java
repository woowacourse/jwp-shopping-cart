package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.SignUpRequest;
import woowacourse.shoppingcart.dto.CustomerUpdateRequest;
import woowacourse.shoppingcart.exception.DuplicateCustomerException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@SpringBootTest
@Transactional
class CustomerServiceTest {

    private static final String EMAIL = "tonic@email.com";
    private static final String PASSWORD = "12345678a";
    private static final String NICKNAME = "토닉";
    private static final String NOT_FOUND_EMAIL = "notFoundEmail@email.com";

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerDao customerDao;

    @BeforeEach
    void setUp() {
        SignUpRequest request = new SignUpRequest(EMAIL, PASSWORD, NICKNAME);
        customerService.registerCustomer(request);
    }

    @DisplayName("정상적으로 회원 등록")
    @Test
    void addCustomer() {
        Customer customer = customerService.findByEmail(EMAIL);
        assertAll(
                () -> assertThat(customer.getEmail()).isEqualTo(EMAIL),
                () -> assertThat(customer.getPassword()).isEqualTo(PASSWORD),
                () -> assertThat(customer.getNickname()).isEqualTo(NICKNAME),
                () -> assertThat(customer.getId()).isNotNull()
        );
    }

    @DisplayName("중복된 email로 회원 등록")
    @Test
    void duplicatedEmailCustomer() {
        SignUpRequest request = new SignUpRequest(EMAIL, PASSWORD, NICKNAME);
        assertThatThrownBy(() -> customerService.registerCustomer(request))
                .isInstanceOf(DuplicateCustomerException.class);
    }

    @DisplayName("email로 회원 조회")
    @Test
    void findByEmail() {
        Customer customer = customerService.findByEmail(EMAIL);

        assertAll(
                () -> assertThat(customer.getEmail()).isEqualTo(EMAIL),
                () -> assertThat(customer.getNickname()).isEqualTo(NICKNAME),
                () -> assertThat(customer.getPassword()).isEqualTo(PASSWORD)
        );
    }

    @DisplayName("가입하지 않은 email로 회원 조회 시 예외 발생")
    @Test
    void notFoundCustomerByEmailThrowException() {
        assertThatThrownBy(() -> customerService.findByEmail(NOT_FOUND_EMAIL))
                .isInstanceOf(InvalidCustomerException.class);
    }

    @DisplayName("존재하지 않는 이메일로 탈퇴 시 예외 발생")
    @Test
    void deleteByNotExistEmail() {
        assertThatThrownBy(() -> customerService.deleteByEmail(NOT_FOUND_EMAIL))
                .isInstanceOf(InvalidCustomerException.class);
    }

    @DisplayName("이메일로 회원 탈퇴")
    @Test
    void deleteByEmail() {
        customerService.deleteByEmail(EMAIL);

        assertThat(customerDao.existByEmail(EMAIL)).isFalse();
    }

    @DisplayName("존재하지 않는 이메일로 수정 시 예외 발생")
    @Test
    void updateByNotExistEmail() {
        assertThatThrownBy(() -> customerService.updateCustomer(NOT_FOUND_EMAIL,
                new CustomerUpdateRequest(NICKNAME, PASSWORD)))
                .isInstanceOf(InvalidCustomerException.class);
    }

    @DisplayName("정상적인 회원 정보 수정")
    @Test
    void updateCustomer() {
        String newNickname = "토닉2";
        String newPassword = "newPassword1";
        customerService.updateCustomer(EMAIL, new CustomerUpdateRequest(newNickname, newPassword));
        Customer customer = customerService.findByEmail(EMAIL);

        assertThat(customer.getPassword()).isEqualTo(newPassword);
        assertThat(customer.getNickname()).isEqualTo(newNickname);
    }
}

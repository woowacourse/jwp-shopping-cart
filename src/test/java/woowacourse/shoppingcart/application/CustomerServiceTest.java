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
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.auth.support.PasswordEncoder;
import woowacourse.shoppingcart.dto.customer.CustomerSignUpRequest;
import woowacourse.shoppingcart.dto.customer.CustomerUpdateRequest;
import woowacourse.shoppingcart.exception.customer.DuplicateCustomerBadRequestException;
import woowacourse.shoppingcart.exception.customer.InvalidCustomerBadRequestException;

@SpringBootTest
@Transactional
class CustomerServiceTest {

    private static final String EMAIL = "tonic@email.com";
    private static final String PASSWORD = "12345678a";
    private static final String ENCRYPT_PASSWORD = PasswordEncoder.encrypt(PASSWORD);
    private static final String NICKNAME = "토닉";
    private static final String NOT_FOUND_EMAIL = "notFoundEmail@email.com";

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerDao customerDao;

    @BeforeEach
    void setUp() {
        CustomerSignUpRequest request = new CustomerSignUpRequest(EMAIL, PASSWORD, NICKNAME);
        customerService.register(request);
    }

    @DisplayName("정상적으로 회원 등록")
    @Test
    void addCustomer() {
        Customer customer = customerDao.findByEmail(EMAIL).get();
        assertAll(
                () -> assertThat(customer.getEmail()).isEqualTo(EMAIL),
                () -> assertThat(customer.getPassword()).isEqualTo(ENCRYPT_PASSWORD),
                () -> assertThat(customer.getNickname()).isEqualTo(NICKNAME),
                () -> assertThat(customer.getId()).isNotNull()
        );
    }

    @DisplayName("중복된 email로 회원 등록")
    @Test
    void duplicatedEmailCustomer() {
        CustomerSignUpRequest request = new CustomerSignUpRequest(EMAIL, PASSWORD, NICKNAME);
        assertThatThrownBy(() -> customerService.register(request))
                .isInstanceOf(DuplicateCustomerBadRequestException.class);
    }

    @DisplayName("email로 회원 조회")
    @Test
    void findByEmail() {
        Customer customer = customerDao.findByEmail(EMAIL).get();

        assertAll(
                () -> assertThat(customer.getEmail()).isEqualTo(EMAIL),
                () -> assertThat(customer.getNickname()).isEqualTo(NICKNAME),
                () -> assertThat(customer.getPassword()).isEqualTo(ENCRYPT_PASSWORD));
    }

    @DisplayName("가입하지 않은 id로 회원 조회 시 예외 발생")
    @Test
    void notFoundCustomerByEmailThrowException() {
        assertThatThrownBy(() -> customerService.findById(0L))
                .isInstanceOf(InvalidCustomerBadRequestException.class);
    }

    @DisplayName("존재하지 않는 이메일로 탈퇴 시 예외 발생")
    @Test
    void deleteByNotExistEmail() {
        Customer customer = new Customer(NOT_FOUND_EMAIL, "notFoundPwd!", "invalid");
        assertThatThrownBy(() -> customerService.delete(customer))
                .isInstanceOf(InvalidCustomerBadRequestException.class);
    }

    @DisplayName("이메일로 회원 탈퇴")
    @Test
    void deleteByEmail() {
        Customer customer = new Customer(EMAIL, PASSWORD, NICKNAME);
        customerService.delete(customer);

        assertThat(customerDao.existByEmail(EMAIL)).isFalse();
    }

    @DisplayName("존재하지 않는 이메일로 수정 시 예외 발생")
    @Test
    void updateByNotExistEmail() {
        Customer notFoundCustomer = new Customer(NOT_FOUND_EMAIL, "invalidPwd1", "invalid");
        assertThatThrownBy(() -> customerService.update(notFoundCustomer,
                new CustomerUpdateRequest(NICKNAME, PASSWORD)))
                .isInstanceOf(InvalidCustomerBadRequestException.class);
    }

    @DisplayName("정상적인 회원 정보 수정")
    @Test
    void updateCustomer() {
        Customer validCustomer = new Customer(EMAIL, PASSWORD, NICKNAME);
        String newNickname = "토닉2";
        String newPassword = "newPassword1";
        customerService.update(validCustomer, new CustomerUpdateRequest(newNickname, newPassword));
        Customer customer = customerDao.findByEmail(EMAIL).get();

        assertThat(customer.getPassword()).isEqualTo(PasswordEncoder.encrypt(newPassword));
        assertThat(customer.getNickname()).isEqualTo(newNickname);
    }
}

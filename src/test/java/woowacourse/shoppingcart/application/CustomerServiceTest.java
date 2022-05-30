package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestConstructor;

import javax.sql.DataSource;
import woowacourse.shoppingcart.dto.CustomerRegisterRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.exception.DuplicatedCustomerEmailException;
import woowacourse.shoppingcart.infrastructure.jdbc.dao.CustomerDao;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class CustomerServiceTest {

    private final CustomerService customerService;

    CustomerServiceTest(final DataSource dataSource) {
        final CustomerDao customerDao = new CustomerDao(dataSource);
        this.customerService = new CustomerService(customerDao);
    }

    @DisplayName("회원을 가입한다.")
    @Test
    void registerCustomer() {
        final CustomerRegisterRequest customerRegisterRequest = new CustomerRegisterRequest(
                "guest@woowa.com", "guest", "qwe123!@#");
        assertDoesNotThrow(() -> customerService.registerCustomer(customerRegisterRequest));
    }

    @DisplayName("중복된 이메일로 회원을 가입할 수 없다.")
    @Test
    void validateCustomerEmailNotDuplicated() {
        final CustomerRegisterRequest customerRegisterRequest = new CustomerRegisterRequest(
                "guest@woowa.com", "guest", "qwe123!@#");
        customerService.registerCustomer(customerRegisterRequest);

        assertThatThrownBy(() -> customerService.registerCustomer(new CustomerRegisterRequest(
                "guest@woowa.com", "guest1", "qwe123!@#")))
                .isInstanceOf(DuplicatedCustomerEmailException.class);
    }

    @DisplayName("회원을 아이디로 조회한다.")
    @Test
    void findById() {
        final CustomerRegisterRequest customerRegisterRequest = new CustomerRegisterRequest(
                "guest@woowa.com", "guest", "qwe123!@#");
        final Long customerId = customerService.registerCustomer(customerRegisterRequest);

        final CustomerResponse customerResponse = customerService.findById(customerId);

        assertThat(customerResponse)
                .extracting("email", "userName")
                .containsExactly("guest@woowa.com", "guest");
    }
}
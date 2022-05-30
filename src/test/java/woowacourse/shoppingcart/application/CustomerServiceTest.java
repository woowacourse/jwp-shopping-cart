package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;
import woowacourse.shoppingcart.dto.CustomerRegisterRequest;
import woowacourse.shoppingcart.exception.DuplicatedCustomerEmailException;
import woowacourse.shoppingcart.infrastructure.jdbc.dao.CustomerDao;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
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
        CustomerRegisterRequest customerRegisterRequest = new CustomerRegisterRequest(
                "guest@woowa.com", "guest", "qwe123!@#");
        assertDoesNotThrow(() -> customerService.registerCustomer(customerRegisterRequest));
    }

    @DisplayName("중복된 이메일로 회원을 가입할 수 없다.")
    @Test
    void validateCustomerEmailNotDuplicated() {
        CustomerRegisterRequest customerRegisterRequest = new CustomerRegisterRequest(
                "guest@woowa.com", "guest", "qwe123!@#");
        customerService.registerCustomer(customerRegisterRequest);

        assertThatThrownBy(() -> customerService.registerCustomer(new CustomerRegisterRequest(
                "guest@woowa.com", "guest1", "qwe123!@#")))
                .isInstanceOf(DuplicatedCustomerEmailException.class);
    }
}
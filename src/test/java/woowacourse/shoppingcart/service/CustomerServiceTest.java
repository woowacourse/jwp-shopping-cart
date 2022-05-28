package woowacourse.shoppingcart.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.SignUpDto;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class CustomerServiceTest {

    private CustomerService customerService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp(){
        customerService = new CustomerService(new CustomerDao(jdbcTemplate));
    }

    @Test
    @DisplayName("회원을 가입시킨다.")
    void signUp() {
        final SignUpDto signUpDto = new SignUpDto("test@test.com", "testtest","테스트");

        final Long createdCustomerId = customerService.signUp(signUpDto);
        final Customer savedCustomer = customerService.findCustomerById(createdCustomerId);

        assertThat(createdCustomerId).isEqualTo(savedCustomer.getId());
    }
}
package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import woowacourse.shoppingcart.application.dto.CustomerDto;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.CustomerFixture;

@SpringBootTest
@DisplayName("CustomerService 는")
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() {
        jdbcTemplate.update("SET FOREIGN_KEY_CHECKS=0");
        jdbcTemplate.update("truncate table orders_detail");
        jdbcTemplate.update("truncate table cart_item");
        jdbcTemplate.update("truncate table orders");
        jdbcTemplate.update("truncate table product");
        jdbcTemplate.update("truncate table customer");
        jdbcTemplate.update("SET FOREIGN_KEY_CHECKS=1");
    }

    @Test
    @DisplayName("회원가입을 할때 회원가입이 정상적으로 이뤄진다.")
    void createCustomer() {
        CustomerDto newCustomer = CustomerFixture.tommyDto;
        final Long customerId = customerService.createCustomer(newCustomer);
        assertThat(customerId).isGreaterThanOrEqualTo(1);
    }
}

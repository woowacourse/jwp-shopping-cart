package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.shoppingcart.Fixtures.CUSTOMER_REQUEST_1;

import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import woowacourse.shoppingcart.dao.AddressDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.JdbcAddressDao;
import woowacourse.shoppingcart.dao.JdbcCustomerDao;
import woowacourse.shoppingcart.dao.JdbcPrivacyDao;
import woowacourse.shoppingcart.dao.PrivacyDao;
import woowacourse.shoppingcart.dto.EmailDuplicationResponse;

@JdbcTest
class CustomerServiceTest {
    private final CustomerService customerService;

    @Autowired
    public CustomerServiceTest(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        CustomerDao customerDao = new JdbcCustomerDao(jdbcTemplate, dataSource);
        PrivacyDao privacyDao = new JdbcPrivacyDao(jdbcTemplate);
        AddressDao addressDao = new JdbcAddressDao(jdbcTemplate);

        this.customerService = new CustomerService(customerDao, privacyDao, addressDao);
    }

    @DisplayName("customer를 생성한다.")
    @Test
    void create() {
        //given
        int id = customerService.create(CUSTOMER_REQUEST_1);

        //when & then
        assertThat(id).isNotNull();
    }

    @DisplayName("email 중복 여부를 반환한다.")
    @Test
    void checkDuplicatedEmail() {
        // given
        customerService.create(CUSTOMER_REQUEST_1);

        //when
        EmailDuplicationResponse response = customerService.isDuplicatedEmail(CUSTOMER_REQUEST_1.getEmail());

        //then
        assertThat(response.getIsDuplicated()).isTrue();
    }
}

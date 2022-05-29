package woowacourse.auth.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import woowacourse.auth.domain.Customer;

@JdbcTest
class CustomerDaoTest {

    private CustomerDao customerDao;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void setUp() {
        customerDao = new CustomerDao(dataSource);
    }

    @Test
    @DisplayName("회원을 저장한다.")
    void save() {
        Customer customer = customerDao.save(new Customer("레넌", "rennon@woowa.com", "1234"));

        assertThat(customer.getId()).isEqualTo(1L);
    }
}

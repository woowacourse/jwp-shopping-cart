package cart.repository;

import cart.entity.CustomerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class CustomerDaoTest {

    @Autowired
    DataSource dataSource;

    CustomerDao customerDao;

    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setting() {
        jdbcTemplate = new JdbcTemplate(dataSource);
        customerDao = new CustomerDao(jdbcTemplate);
    }

    @DisplayName("현재 등록되어 있는 모든 유저의 정보를 가져온다")
    @Test
    void finaAll() {
        //given,when
        List<CustomerEntity> customers = customerDao.findAll();

        //then
        assertThat(customers).hasSize(2);
    }
}

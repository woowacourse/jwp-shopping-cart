package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import cart.dao.entity.CustomerEntity;
import cart.dao.entity.CustomerEntity.Builder;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class CustomerDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private CustomerDao customerDao;

    private String email = "baron@gmail.com";
    private CustomerEntity customerEntity = new Builder()
            .email(email)
            .password("password")
            .build();

    @BeforeEach
    void setUp() {
        this.customerDao = new CustomerDao(jdbcTemplate);
    }

    @DisplayName("새로운 사용자를 저장한다.")
    @Test
    void insert() {
        // when
        customerDao.insert(customerEntity);

        // then
        List<CustomerEntity> customers = customerDao.findAll();
        assertThat(customers.get(0))
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(customerEntity);
    }

    @DisplayName("이메일로 저장된 사용자를 조회한다.")
    @Test
    void findByEmail() {
        // given
        long savedId = customerDao.insert(customerEntity);

        // when
        Optional<CustomerEntity> foundCustomer = customerDao.findByEmail(email);

        // then
        assertThat(foundCustomer.isPresent()).isTrue();
    }

    @DisplayName("전체 사용자를 조회한다.")
    @Test
    void findAll() {
        // given
        customerDao.insert(customerEntity);
        customerDao.insert(customerEntity);

        // when
        List<CustomerEntity> customers = customerDao.findAll();

        // then
        assertThat(customers).hasSize(2);
    }
}
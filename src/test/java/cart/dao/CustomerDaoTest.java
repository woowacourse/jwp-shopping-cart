package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.entity.customer.CustomerEntity;
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
    JdbcTemplate jdbcTemplate;

    private CustomerDao customerDao;

    @BeforeEach
    void setUp() {
        customerDao = new CustomerDao(jdbcTemplate);
    }

    @DisplayName("고객을 DB에 저장한다.")
    @Test
    void save() {
        //given
        final CustomerEntity customer = new CustomerEntity(null, "a@email.com", "password");

        //when
        final Long savedId = customerDao.save(customer);

        //then
        final List<CustomerEntity> customers = customerDao.findAll();
        assertThat(customers.get(customers.size() - 1).getId()).isEqualTo(savedId);
    }

    @DisplayName("모든 고객의 정보를 조회하여 반환한다. (어플리케이션 실행시 더미 유저 2명 존재)")
    @Test
    void findAll() {
        //given
        //when
        List<CustomerEntity> customers = customerDao.findAll();

        //then
        assertThat(customers).hasSize(2);
    }

    @DisplayName("이메일과 비밀번호이 일치하는 고객의 아이디를 조회하여 반환한다.")
    @Test
    void findIdByBasicCredential() {
        //given
        final Long savedId = customerDao.save(new CustomerEntity(null, "email@email.com", "password"));

        //when
        final Optional<Long> findId = customerDao.findIdByEmailAndPassword("email@email.com", "password");

        //then
        assertAll(
            () -> assertThat(findId).isPresent(),
            () -> assertThat(findId.get()).isEqualTo(savedId)
        );
    }
}

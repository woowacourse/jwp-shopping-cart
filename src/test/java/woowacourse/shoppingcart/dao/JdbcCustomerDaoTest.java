package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static woowacourse.shoppingcart.fixture.CustomerFixtures.CUSTOMER_1;
import static woowacourse.shoppingcart.fixture.CustomerFixtures.CUSTOMER_1_UPDATED_PASSWORD;

import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import woowacourse.shoppingcart.entity.CustomerEntity;

@JdbcTest
class JdbcCustomerDaoTest {
    private final CustomerDao customerDao;

    @Autowired
    public JdbcCustomerDaoTest(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        customerDao = new JdbcCustomerDao(jdbcTemplate, dataSource);
    }

    @DisplayName("CustomerEntity를 전달받아 데이터베이스에 추가한다.")
    @Test
    void save() {
        // when
        int actual = customerDao.save(CUSTOMER_1);

        // then
        assertThat(actual).isNotNull();
    }

    @DisplayName("Customer id를 전달받아 해당하는 Customer 객체를 조회한다.")
    @Test
    void findById() {
        //given
        int customerId = customerDao.save(CUSTOMER_1);

        // when
        CustomerEntity actual = customerDao.findById(customerId);

        // then
        assertThat(actual).extracting("id", "email", "password", "profileImageUrl", "terms")
                .containsExactly(customerId, CUSTOMER_1.getEmail().getValue(), CUSTOMER_1.getPassword().getValue(),
                        CUSTOMER_1.getProfileImageUrl().getValue(), CUSTOMER_1.isTerms());
    }

    @DisplayName("Customer email을 전달받아 해당하는 Customer 객체를 조회한다.")
    @Test
    void findByEmail() {
        //given
        int customerId = customerDao.save(CUSTOMER_1);

        // when
        CustomerEntity actual = customerDao.findByEmail(CUSTOMER_1.getEmail().getValue());

        // then
        assertThat(actual).extracting("id", "email", "password", "profileImageUrl", "terms")
                .containsExactly(customerId, CUSTOMER_1.getEmail().getValue(), CUSTOMER_1.getPassword().getValue(),
                        CUSTOMER_1.getProfileImageUrl().getValue(), CUSTOMER_1.isTerms());
    }

    @DisplayName("Customer와 id를 전달받아 해당하는 Customer를 수정한다.")
    @Test
    void update() {
        // given
        int customerId = customerDao.save(CUSTOMER_1);

        // when
        customerDao.update(customerId, CUSTOMER_1_UPDATED_PASSWORD);
        CustomerEntity actual = customerDao.findById(customerId);

        // then
        assertThat(actual).extracting("id", "email", "password", "profileImageUrl", "terms")
                .containsExactly(customerId, CUSTOMER_1_UPDATED_PASSWORD.getEmail().getValue(),
                        CUSTOMER_1_UPDATED_PASSWORD.getPassword().getValue(),
                        CUSTOMER_1_UPDATED_PASSWORD.getProfileImageUrl().getValue(),
                        CUSTOMER_1_UPDATED_PASSWORD.isTerms());
    }

    @DisplayName("id를 전달받아 해당하는 Customer를 삭제한다.")
    @Test
    void delete() {
        // given
        int customerId = customerDao.save(CUSTOMER_1);

        // when
        customerDao.delete(customerId);

        // then
        assertThatThrownBy(() -> customerDao.findById(customerId))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("email을 전달받아 존재여부를 반환한다.")
    @CsvSource(value = {"devhudi@gmail.com,true", "notexists@gmail.com,false"})
    @ParameterizedTest
    void checkEmailExists(String email, boolean expected) {
        // given
        customerDao.save(CUSTOMER_1);

        // when
        boolean actual = customerDao.hasEmail(email);

        // then
        assertThat(actual).isEqualTo(expected);

    }

}

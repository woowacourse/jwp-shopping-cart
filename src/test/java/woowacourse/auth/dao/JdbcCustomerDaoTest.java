package woowacourse.auth.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.auth.Fixtures.CUSTOMER_ENTITY_1;

import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.auth.entity.CustomerEntity;

@Sql(scripts = {"classpath:schema.sql"})
@JdbcTest
class JdbcCustomerDaoTest {
    private final CustomerDao customerDao;

    @Autowired
    public JdbcCustomerDaoTest(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        customerDao = new JdbcCustomerDao(jdbcTemplate, dataSource);
    }

    @DisplayName("User 도메인 객체를 전달받아 데이터베이스에 추가한다.")
    @Test
    void save() {
        // when
        int actual = customerDao.save(CUSTOMER_ENTITY_1);

        // then
        assertThat(actual).isNotNull();
    }

    @DisplayName("User id를 전달받아 해당하는 User 객체를 조회한다.")
    @Test
    void findById() {
        //given
        int userId = customerDao.save(CUSTOMER_ENTITY_1);

        // when
        CustomerEntity actual = customerDao.findById(userId);

        // then
        assertThat(actual).extracting("id", "email", "password", "profileImageUrl", "terms")
                .containsExactly(userId, CUSTOMER_ENTITY_1.getEmail(), CUSTOMER_ENTITY_1.getPassword(),
                        CUSTOMER_ENTITY_1.getProfileImageUrl(), CUSTOMER_ENTITY_1.isTerms());
    }

    @DisplayName("User email을 전달받아 해당하는 User 객체를 조회한다.")
    @Test
    void findByEmail() {
        //given
        int userId = customerDao.save(CUSTOMER_ENTITY_1);

        // when
        CustomerEntity actual = customerDao.findByEmail(CUSTOMER_ENTITY_1.getEmail());

        // then
        assertThat(actual).extracting("id", "email", "password", "profileImageUrl", "terms")
                .containsExactly(userId, CUSTOMER_ENTITY_1.getEmail(), CUSTOMER_ENTITY_1.getPassword(),
                        CUSTOMER_ENTITY_1.getProfileImageUrl(), CUSTOMER_ENTITY_1.isTerms());
    }
}

package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static woowacourse.shoppingcart.Fixtures.CUSTOMER_ENTITY_1;
import static woowacourse.shoppingcart.Fixtures.PRIVACY_ENTITY_1;

import java.time.LocalDate;
import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import woowacourse.shoppingcart.entity.PrivacyEntity;

@JdbcTest
class JdbcPrivacyDaoTest {
    private final PrivacyDao privacyDao;
    private final CustomerDao customerDao;

    @Autowired
    public JdbcPrivacyDaoTest(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        privacyDao = new JdbcPrivacyDao(jdbcTemplate);
        customerDao = new JdbcCustomerDao(jdbcTemplate, dataSource);
    }

    @DisplayName("PrivacyEntity를 전달받아 데이터베이스에 추가한다.")
    @Test
    void save() {
        // given
        int customerId = customerDao.save(CUSTOMER_ENTITY_1);

        // when
        privacyDao.save(customerId, PRIVACY_ENTITY_1);
        PrivacyEntity privacyEntity = privacyDao.findById(customerId);

        // then
        assertThat(privacyEntity).isNotNull();
    }

    @DisplayName("Customer id를 전달받아 해당하는 객체를 조회하여 PrivacyEntity를 반환한다.")
    @Test
    void findById() {
        // given
        int customerId = customerDao.save(CUSTOMER_ENTITY_1);
        privacyDao.save(customerId, PRIVACY_ENTITY_1);

        // when
        PrivacyEntity actual = privacyDao.findById(customerId);

        // then
        assertThat(actual).extracting("customerId", "name", "gender", "birthday", "contact")
                .containsExactly(customerId, PRIVACY_ENTITY_1.getName(), PRIVACY_ENTITY_1.getGender(),
                        PRIVACY_ENTITY_1.getBirthday(), PRIVACY_ENTITY_1.getContact());
    }

    @DisplayName("PrivacyEntity와 Customer id를 전달받아 해당하는 Privacy를 수정한다.")
    @Test
    void update() {
        // given
        int customerId = customerDao.save(CUSTOMER_ENTITY_1);
        privacyDao.save(customerId, PRIVACY_ENTITY_1);

        // when
        PrivacyEntity newPrivacyEntity = new PrivacyEntity("새로운 이름", "female", LocalDate.of(1999, 12, 21),
                "01033334444");
        privacyDao.update(customerId, newPrivacyEntity);

        PrivacyEntity actual = privacyDao.findById(customerId);

        // then
        assertThat(actual).extracting("customerId", "name", "gender", "birthday", "contact")
                .containsExactly(customerId, newPrivacyEntity.getName(), newPrivacyEntity.getGender(),
                        newPrivacyEntity.getBirthday(), newPrivacyEntity.getContact());
    }

    @DisplayName("id를 전달받아 해당하는 Privacy를 삭제한다.")
    @Test
    void delete() {
        // given
        int customerId = customerDao.save(CUSTOMER_ENTITY_1);
        privacyDao.save(customerId, PRIVACY_ENTITY_1);

        // when
        privacyDao.delete(customerId);

        // then
        assertThatThrownBy(() -> privacyDao.findById(customerId))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }
}

package woowacourse.auth.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.auth.Fixtures.CUSTOMER_ENTITY_1;
import static woowacourse.auth.Fixtures.PRIVACY_ENTITY_1;

import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import woowacourse.auth.entity.PrivacyEntity;

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
        assertThat(actual).extracting("customerId", "name", "gender", "birthDay", "contact")
                .containsExactly(customerId, PRIVACY_ENTITY_1.getName(), PRIVACY_ENTITY_1.getGender(),
                        PRIVACY_ENTITY_1.getBirthDay(), PRIVACY_ENTITY_1.getContact());
    }
//
//    @DisplayName("Customer email을 전달받아 해당하는 Customer 객체를 조회한다.")
//    @Test
//    void findByEmail() {
//        //given
//        int userId = customerDao.save(CUSTOMER_ENTITY_1);
//
//        // when
//        CustomerEntity actual = customerDao.findByEmail(CUSTOMER_ENTITY_1.getEmail());
//
//        // then
//        assertThat(actual).extracting("id", "email", "password", "profileImageUrl", "terms")
//                .containsExactly(userId, CUSTOMER_ENTITY_1.getEmail(), CUSTOMER_ENTITY_1.getPassword(),
//                        CUSTOMER_ENTITY_1.getProfileImageUrl(), CUSTOMER_ENTITY_1.isTerms());
//    }
//
//    @DisplayName("CustomerEntity와 id를 전달받아 해당하는 Customer를 수정한다.")
//    @Test
//    void update() {
//        // given
//        int userId = customerDao.save(CUSTOMER_ENTITY_1);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String encryptedNewPassword = passwordEncoder.encode("newpassword1!");
//        CustomerEntity newCustomerEntity = new CustomerEntity(userId, CUSTOMER_ENTITY_1.getEmail(),
//                encryptedNewPassword,
//                "http://gravatar.com/avatar/2?d=identicon", true);
//
//        // when
//        customerDao.update(newCustomerEntity);
//        CustomerEntity actual = customerDao.findById(userId);
//
//        // then
//        assertThat(actual).extracting("id", "email", "password", "profileImageUrl", "terms")
//                .containsExactly(userId, newCustomerEntity.getEmail(), newCustomerEntity.getPassword(),
//                        newCustomerEntity.getProfileImageUrl(), newCustomerEntity.isTerms());
//    }
//
//    @DisplayName("id를 전달받아 해당하는 Customer를 삭제한다.")
//    @Test
//    void delete() {
//        // given
//        int userId = customerDao.save(CUSTOMER_ENTITY_1);
//
//        // when
//        customerDao.delete(userId);
//
//        // then
//        assertThatThrownBy(() -> customerDao.findById(userId))
//                .isInstanceOf(EmptyResultDataAccessException.class);
//    }
}

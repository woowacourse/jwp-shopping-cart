package woowacourse.auth.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.auth.Fixtures.ADDRESS_ENTITY_1;
import static woowacourse.auth.Fixtures.CUSTOMER_ENTITY_1;

import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import woowacourse.auth.entity.AddressEntity;

@JdbcTest
class JdbcAddressDaoTest {
    private final AddressDao addressDao;
    private final CustomerDao customerDao;

    @Autowired
    public JdbcAddressDaoTest(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        addressDao = new JdbcAddressDao(jdbcTemplate);
        customerDao = new JdbcCustomerDao(jdbcTemplate, dataSource);
    }

    @DisplayName("AddressEntity를 전달받아 데이터베이스에 추가한다.")
    @Test
    void save() {
        // given
        int customerId = customerDao.save(CUSTOMER_ENTITY_1);

        // when
        addressDao.save(customerId, ADDRESS_ENTITY_1);
        AddressEntity addressEntity = addressDao.findById(customerId);

        // then
        assertThat(addressEntity).isNotNull();
    }

    @DisplayName("Customer id를 전달받아 해당하는 객체를 조회하여 PrivacyEntity를 반환한다.")
    @Test
    void findById() {
        // given
        int customerId = customerDao.save(CUSTOMER_ENTITY_1);
        addressDao.save(customerId, ADDRESS_ENTITY_1);

        // when
        AddressEntity actual = addressDao.findById(customerId);

        // then
        assertThat(actual).extracting("customerId", "address", "detailAddress", "zoneCode")
                .containsExactly(customerId, actual.getAddress(), actual.getDetailAddress(), actual.getZoneCode());
    }
}

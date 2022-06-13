package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static woowacourse.shoppingcart.fixture.CustomerFixtures.CUSTOMER_1;
import static woowacourse.shoppingcart.fixture.CustomerFixtures.FULL_ADDRESS_1;
import static woowacourse.shoppingcart.fixture.CustomerFixtures.FULL_ADDRESS_2;

import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import woowacourse.shoppingcart.entity.AddressEntity;

@JdbcTest
class JdbcAddressDaoTest {
    private final AddressDao addressDao;
    private final CustomerDao customerDao;

    @Autowired
    public JdbcAddressDaoTest(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        addressDao = new JdbcAddressDao(jdbcTemplate);
        customerDao = new JdbcCustomerDao(jdbcTemplate, dataSource);
    }

    @DisplayName("fullAddress를 전달받아 데이터베이스에 추가한다.")
    @Test
    void save() {
        // given
        int customerId = customerDao.save(CUSTOMER_1);

        // when
        addressDao.save(customerId, FULL_ADDRESS_1);
        AddressEntity addressEntity = addressDao.findById(customerId);

        // then
        assertThat(addressEntity).isNotNull();
    }

    @DisplayName("Customer id를 전달받아 해당하는 객체를 조회하여 PrivacyEntity를 반환한다.")
    @Test
    void findById() {
        // given
        int customerId = customerDao.save(CUSTOMER_1);
        addressDao.save(customerId, FULL_ADDRESS_1);

        // when
        AddressEntity actual = addressDao.findById(customerId);

        // then
        assertThat(actual).extracting("customerId", "address", "detailAddress", "zoneCode")
                .containsExactly(customerId, actual.getAddress(), actual.getDetailAddress(), actual.getZoneCode());
    }


    @DisplayName("Address와 Customer id를 전달받아 해당하는 Address를 수정한다.")
    @Test
    void update() {
        // given
        int customerId = customerDao.save(CUSTOMER_1);
        addressDao.save(customerId, FULL_ADDRESS_1);

        // when
        addressDao.update(customerId, FULL_ADDRESS_2);

        AddressEntity actual = addressDao.findById(customerId);

        // then
        assertThat(actual).extracting("customerId", "address", "detailAddress", "zoneCode")
                .containsExactly(customerId, FULL_ADDRESS_2.getAddress().getValue(),
                        FULL_ADDRESS_2.getDetailAddress().getValue(),
                        FULL_ADDRESS_2.getZoneCode().getValue());
    }

    @DisplayName("id를 전달받아 해당하는 Address를 삭제한다.")
    @Test
    void delete() {
        // given
        int customerId = customerDao.save(CUSTOMER_1);
        addressDao.save(customerId, FULL_ADDRESS_1);

        // when
        addressDao.delete(customerId);

        // then
        assertThatThrownBy(() -> addressDao.findById(customerId))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }
}

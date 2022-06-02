package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static woowacourse.Fixtures.ADDRESS_ENTITY_1;
import static woowacourse.Fixtures.CUSTOMER_ENTITY_1;

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


    @DisplayName("AddressEntity와 Customer id를 전달받아 해당하는 Address를 수정한다.")
    @Test
    void update() {
        // given
        int customerId = customerDao.save(CUSTOMER_ENTITY_1);
        addressDao.save(customerId, ADDRESS_ENTITY_1);

        // when
        AddressEntity newAddressEntity = new AddressEntity("경기도 양주시", "옥정동 배카라하우스", "12312");
        addressDao.update(customerId, newAddressEntity);

        AddressEntity actual = addressDao.findById(customerId);

        // then
        assertThat(actual).extracting("customerId", "address", "detailAddress", "zoneCode")
                .containsExactly(customerId, newAddressEntity.getAddress(), newAddressEntity.getDetailAddress(),
                        newAddressEntity.getZoneCode());
    }

    @DisplayName("id를 전달받아 해당하는 Address를 삭제한다.")
    @Test
    void delete() {
        // given
        int customerId = customerDao.save(CUSTOMER_ENTITY_1);
        addressDao.save(customerId, ADDRESS_ENTITY_1);

        // when
        addressDao.delete(customerId);

        // then
        assertThatThrownBy(() -> addressDao.findById(customerId))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }
}

package woowacourse.shoppingcart.service.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.Fixtures.ADDRESS_VALUE_1;
import static woowacourse.Fixtures.CUSTOMER_ENTITY_1;
import static woowacourse.Fixtures.DETAIL_ADDRESS_VALUE_1;
import static woowacourse.Fixtures.ZONE_CODE_VALUE_1;

import java.util.Optional;
import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import woowacourse.shoppingcart.dao.AddressDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.JdbcAddressDao;
import woowacourse.shoppingcart.dao.JdbcCustomerDao;
import woowacourse.shoppingcart.entity.AddressEntity;

@JdbcTest
class JdbcAddressDaoTest {
    private final AddressDao addressDao;
    private final CustomerDao customerDao;

    @Autowired
    public JdbcAddressDaoTest(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        addressDao = new JdbcAddressDao(jdbcTemplate, dataSource);
        customerDao = new JdbcCustomerDao(jdbcTemplate, dataSource);
    }

    @DisplayName("AddressEntity를 전달받아 데이터베이스에 추가한다.")
    @Test
    void save() {
        // given
        long customerId = customerDao.save(CUSTOMER_ENTITY_1);
        AddressEntity addressEntity = new AddressEntity(customerId, ADDRESS_VALUE_1, DETAIL_ADDRESS_VALUE_1,
                ZONE_CODE_VALUE_1);

        // when
        addressDao.save(customerId, addressEntity);
        AddressEntity actual = addressDao.findById(customerId).get();

        // then
        assertThat(actual).isNotNull();
    }

    @DisplayName("Customer id를 전달받아 해당하는 객체를 조회하여 PrivacyEntity를 반환한다.")
    @Test
    void findById() {
        // given
        long customerId = customerDao.save(CUSTOMER_ENTITY_1);
        AddressEntity addressEntity = new AddressEntity(customerId, ADDRESS_VALUE_1, DETAIL_ADDRESS_VALUE_1,
                ZONE_CODE_VALUE_1);
        addressDao.save(customerId, addressEntity);

        // when
        AddressEntity actual = addressDao.findById(customerId).get();

        // then
        assertThat(actual).extracting("customerId", "address", "detailAddress", "zonecode")
                .containsExactly(customerId, actual.getAddress(), actual.getDetailAddress(), actual.getZonecode());
    }


    @DisplayName("AddressEntity와 Customer id를 전달받아 해당하는 Address를 수정한다.")
    @Test
    void update() {
        // given
        long customerId = customerDao.save(CUSTOMER_ENTITY_1);
        AddressEntity addressEntity = new AddressEntity(customerId, ADDRESS_VALUE_1, DETAIL_ADDRESS_VALUE_1,
                ZONE_CODE_VALUE_1);
        addressDao.save(customerId, addressEntity);

        // when
        AddressEntity newAddressEntity = new AddressEntity(customerId, "경기도 양주시", "옥정동 배카라하우스", "12312");
        addressDao.update(customerId, newAddressEntity);

        AddressEntity actual = addressDao.findById(customerId).get();

        // then
        assertThat(actual).extracting("customerId", "address", "detailAddress", "zonecode")
                .containsExactly(customerId, newAddressEntity.getAddress(), newAddressEntity.getDetailAddress(),
                        newAddressEntity.getZonecode());
    }

    @DisplayName("id를 전달받아 해당하는 Address를 삭제한다.")
    @Test
    void delete() {
        // given
        long customerId = customerDao.save(CUSTOMER_ENTITY_1);
        AddressEntity addressEntity = new AddressEntity(customerId, ADDRESS_VALUE_1, DETAIL_ADDRESS_VALUE_1,
                ZONE_CODE_VALUE_1);
        addressDao.save(customerId, addressEntity);

        // when
        addressDao.delete(customerId);

        // then
        assertThat(addressDao.findById(customerId)).isEqualTo(Optional.empty());
    }
}

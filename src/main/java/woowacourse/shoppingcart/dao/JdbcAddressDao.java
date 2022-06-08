package woowacourse.shoppingcart.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.customer.address.Address;
import woowacourse.shoppingcart.domain.customer.address.FullAddress;
import woowacourse.shoppingcart.entity.AddressEntity;

@Repository
public class JdbcAddressDao implements AddressDao {
    private static final RowMapper<AddressEntity> ADDRESS_ENTITY_ROW_MAPPER = (rs, rowNum) -> new AddressEntity(
            rs.getInt("customer_id"),
            rs.getString("address"),
            rs.getString("detail_address"),
            rs.getString("zone_code")
    );

    private final JdbcTemplate jdbcTemplate;

    public JdbcAddressDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(int customerId, FullAddress fullAddress) {
        String sql = "INSERT INTO full_address (customer_id, address, detail_address, zone_code) VALUES(?, ?, ?,?)";
        jdbcTemplate.update(sql, customerId, fullAddress.getAddress().getValue(),
                fullAddress.getDetailAddress().getValue(),
                fullAddress.getZoneCode().getValue());
    }

    @Override
    public AddressEntity findById(int customerId) {
        String sql = "SELECT customer_id, address, detail_address, zone_code FROM full_address WHERE customer_id = ?";
        return jdbcTemplate.queryForObject(sql, ADDRESS_ENTITY_ROW_MAPPER, customerId);
    }

    @Override
    public void update(int customerId, FullAddress fullAddress) {
        String sql = "UPDATE full_address SET address = ?, detail_address = ?, zone_code = ? WHERE customer_id = ?";
        jdbcTemplate.update(sql, fullAddress.getAddress().getValue(),
                fullAddress.getDetailAddress().getValue(),
                fullAddress.getZoneCode().getValue(), customerId);
    }

    @Override
    public void delete(int customerId) {
        String sql = "DELETE FROM full_address WHERE customer_id = ?";
        jdbcTemplate.update(sql, customerId);
    }
}

package woowacourse.auth.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import woowacourse.auth.entity.AddressEntity;

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
    public void save(int customerId, AddressEntity addressEntity) {
        String sql = "INSERT INTO FULL_ADDRESS (customer_id, address, detail_address, zone_code) VALUES(?, ?, ?,?)";
        jdbcTemplate.update(sql, customerId, addressEntity.getAddress(), addressEntity.getDetailAddress(),
                addressEntity.getZoneCode());
    }

    @Override
    public AddressEntity findById(int id) {
        String sql = "SELECT customer_id, address, detail_address, zone_code FROM FULL_ADDRESS WHERE customer_id = ?";
        return jdbcTemplate.queryForObject(sql, ADDRESS_ENTITY_ROW_MAPPER, id);
    }

    @Override
    public void update(int customerId, AddressEntity addressEntity) {

    }

    @Override
    public void delete(int customerId) {

    }
}

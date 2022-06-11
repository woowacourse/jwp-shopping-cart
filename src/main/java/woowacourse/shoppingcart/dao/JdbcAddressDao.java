package woowacourse.shoppingcart.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
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
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcAddressDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void save(long customerId, AddressEntity addressEntity) {
        String sql = "INSERT INTO full_address (customer_id, address, detail_address, zone_code) VALUES(:customerId, :address, :detailAddress, :zonecode)";

        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("address", addressEntity.getAddress());
        params.put("detailAddress", addressEntity.getDetailAddress());
        params.put("zonecode", addressEntity.getZonecode());

        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public Optional<AddressEntity> findById(long customerId) {
        try {
            String sql = "SELECT customer_id, address, detail_address, zone_code FROM full_address WHERE customer_id = ?";
            return Optional.of(jdbcTemplate.queryForObject(sql, ADDRESS_ENTITY_ROW_MAPPER, customerId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void update(long customerId, AddressEntity addressEntity) {
        String sql = "UPDATE full_address SET address = :address, detail_address = :detailAddress, zone_code = :zonecode WHERE customer_id = :customerId";

        Map<String, Object> params = new HashMap<>();
        params.put("address", addressEntity.getAddress());
        params.put("detailAddress", addressEntity.getDetailAddress());
        params.put("zonecode", addressEntity.getZonecode());
        params.put("customerId", customerId);

        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void delete(long customerId) {
        String sql = "DELETE FROM full_address WHERE customer_id = ?";
        jdbcTemplate.update(sql, customerId);
    }
}

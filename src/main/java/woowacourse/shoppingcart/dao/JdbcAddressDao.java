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
    private static final String CUSTOMER_ID_COLUMN = "customer_id";
    private static final String ADDRESS_COLUMN = "address";
    private static final String DETAIL_ADDRESS_COLUMN = "detail_address";
    private static final String ZONE_CODE_COLUMN = "zone_code";

    private static final RowMapper<AddressEntity> ADDRESS_ENTITY_ROW_MAPPER = (rs, rowNum) -> new AddressEntity(
            rs.getLong(CUSTOMER_ID_COLUMN),
            rs.getString(ADDRESS_COLUMN),
            rs.getString(DETAIL_ADDRESS_COLUMN),
            rs.getString(ZONE_CODE_COLUMN)
    );

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcAddressDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void save(long customerId, AddressEntity addressEntity) {
        String sql = "INSERT INTO full_address (customer_id, address, detail_address, zone_code) VALUES(:customer_id, :address, :detail_address, :zone_code)";

        Map<String, Object> params = new HashMap<>();
        params.put(CUSTOMER_ID_COLUMN, customerId);
        params.put(ADDRESS_COLUMN, addressEntity.getAddress());
        params.put(DETAIL_ADDRESS_COLUMN, addressEntity.getDetailAddress());
        params.put(ZONE_CODE_COLUMN, addressEntity.getZonecode());

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
        String sql = "UPDATE full_address SET address = :address, detail_address = :detail_address, zone_code = :zone_code WHERE customer_id = :customer_id";

        Map<String, Object> params = new HashMap<>();
        params.put(ADDRESS_COLUMN, addressEntity.getAddress());
        params.put(DETAIL_ADDRESS_COLUMN, addressEntity.getDetailAddress());
        params.put(ZONE_CODE_COLUMN, addressEntity.getZonecode());
        params.put(CUSTOMER_ID_COLUMN, customerId);

        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void delete(long customerId) {
        String sql = "DELETE FROM full_address WHERE customer_id = ?";
        jdbcTemplate.update(sql, customerId);
    }
}

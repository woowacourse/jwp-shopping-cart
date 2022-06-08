package woowacourse.shoppingcart.dao;

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
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
    public void save(AddressEntity addressEntity) {
        String sql = "INSERT INTO full_address (customer_id, address, detail_address, zone_code) VALUES(:customerId, :address, :detailAddress, :zonecode)";

        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(addressEntity);
        namedParameterJdbcTemplate.update(sql, sqlParameterSource);
    }

    @Override
    public AddressEntity findById(int id) {
        String sql = "SELECT customer_id, address, detail_address, zone_code FROM full_address WHERE customer_id = ?";
        return jdbcTemplate.queryForObject(sql, ADDRESS_ENTITY_ROW_MAPPER, id);
    }

    @Override
    public void update(AddressEntity addressEntity) {
        String sql = "UPDATE full_address SET address = :address, detail_address = :detailAddress, zone_code = :zonecode WHERE customer_id = :customerId";

        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(addressEntity);
        namedParameterJdbcTemplate.update(sql, sqlParameterSource);
    }

    @Override
    public void delete(int customerId) {
        String sql = "DELETE FROM full_address WHERE customer_id = ?";
        jdbcTemplate.update(sql, customerId);
    }
}

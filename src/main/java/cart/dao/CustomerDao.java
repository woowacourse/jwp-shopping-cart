package cart.dao;

import cart.entity.customer.CustomerEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDao {

    private static final RowMapper<CustomerEntity> CUSTOMER_ENTITY_ROW_MAPPER = ((rs, rowNum) -> {
        final Long id = rs.getLong("id");
        final String email = rs.getString("email");
        final String password = rs.getString("password");
        return new CustomerEntity(id, email, password);
    });
    private static final RowMapper<Long> LONG_ROW_MAPPER = (rs, rowNum) -> rs.getLong("id");

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public CustomerDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("CUSTOMER")
            .usingColumns("password", "email")
            .usingGeneratedKeyColumns("id");
    }

    public Long save(final CustomerEntity customerEntity) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("email", customerEntity.getEmail());
        params.addValue("password", customerEntity.getPassword());

        return simpleJdbcInsert.executeAndReturnKey(params)
            .longValue();
    }

    public List<CustomerEntity> findAll() {
        final String sql = "SELECT * FROM CUSTOMER";

        return jdbcTemplate.query(sql, CUSTOMER_ENTITY_ROW_MAPPER);
    }

    public Optional<Long> findIdByEmailAndPassword(final String email, final String password) {
        final String sql = "SELECT id FROM CUSTOMER WHERE email = ? AND password = ?";
        final List<Long> customerIds = jdbcTemplate.query(sql, LONG_ROW_MAPPER, email, password);

        if (customerIds.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(customerIds.get(0));
    }
}

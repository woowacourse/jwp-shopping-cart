package cart.dao;

import cart.dao.entity.CustomerEntity;
import cart.dao.entity.CustomerEntity.Builder;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public CustomerDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("customer")
                .usingGeneratedKeyColumns("id");
    }

    public long insert(final CustomerEntity customerEntity) {
        Number key = simpleJdbcInsert.executeAndReturnKey(Map.of("email", customerEntity.getEmail(), "password",
                customerEntity.getPassword()));
        return key.longValue();
    }

    public Optional<CustomerEntity> findByEmail(final String email) {
        String sql = "SELECT * FROM customer WHERE email = ?";
        try {
            CustomerEntity customerEntity = jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                    new Builder()
                            .id(rs.getLong("id"))
                            .email(rs.getString("email"))
                            .password(rs.getString("password"))
                            .build(), email);
            return Optional.ofNullable(customerEntity);
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    public List<CustomerEntity> findAll() {
        String sql = "SELECT * FROM customer";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
            new CustomerEntity.Builder()
                    .id(rs.getLong("id"))
                    .email(rs.getString("email"))
                    .password(rs.getString("password"))
                    .build());
    }
}

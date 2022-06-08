package woowacourse.shoppingcart.infra.dao;

import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import woowacourse.shoppingcart.infra.dao.entity.CustomerEntity;

@Component
public class JdbcCustomerDao implements CustomerDao {
    private static final RowMapper<CustomerEntity> CUSTOMER_ENTITY_ROW_MAPPER =
            (rs, rowNum) -> new CustomerEntity(
                    rs.getLong("id"),
                    rs.getString("email"),
                    rs.getString("name"),
                    rs.getString("password"));

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JdbcCustomerDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("customer")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public long save(CustomerEntity customerEntity) {
        final Number number = simpleJdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(customerEntity));
        return number.longValue();
    }

    @Override
    public Optional<CustomerEntity> findById(long id) {
        try {
            final String sql = "SELECT id, email, name, password FROM customer WHERE id = ?";
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, CUSTOMER_ENTITY_ROW_MAPPER, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<CustomerEntity> findByEmail(String email) {
        try {
            final String sql = "SELECT id, email, name, password FROM customer WHERE email = ?";
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, CUSTOMER_ENTITY_ROW_MAPPER, email));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<CustomerEntity> findByName(String name) {
        try {
            final String sql = "SELECT id, email, name, password FROM customer WHERE name = ?";
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, CUSTOMER_ENTITY_ROW_MAPPER, name));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void update(CustomerEntity customerEntity) {
        final String sql = "UPDATE customer SET email = ?, name = ?, password = ? WHERE id = ?";

        jdbcTemplate.update(sql, customerEntity.getEmail(), customerEntity.getName(), customerEntity.getPassword(),
                customerEntity.getId());
    }

    @Override
    public void deleteById(long id) {
        final String sql = "DELETE FROM customer WHERE id = ?";

        jdbcTemplate.update(sql, id);
    }
}

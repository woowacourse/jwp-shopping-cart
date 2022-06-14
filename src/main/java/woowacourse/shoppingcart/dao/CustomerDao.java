package woowacourse.shoppingcart.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.HashedPassword;

@Repository
public class CustomerDao {

    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    public CustomerDao(final NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate;
    }

    private static Customer rowMapper(final ResultSet rs, int row) throws SQLException {
        return new Customer(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("email"),
                new HashedPassword(rs.getString("password"))
        );
    }

    public Customer save(final Customer customer) {
        final String sql = "INSERT INTO customer(name, email, password) VALUES (:name, :email, :password)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        final Map<String, Object> params = new HashMap<>();
        params.put("name", customer.getName());
        params.put("email", customer.getEmail().getValue());
        params.put("password", customer.getPassword().getHashedValue());

        namedJdbcTemplate.update(sql, new MapSqlParameterSource(params), keyHolder);
        final long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return new Customer(id, customer.getName(), customer.getEmail().getValue(), customer.getPassword());
    }

    public boolean existsByEmail(final Customer customer) {
        final String sql = "SELECT EXISTS (SELECT * FROM customer WHERE email = :email)";
        final Map<String, Object> params = new HashMap<>();
        params.put("email", customer.getEmail().getValue());

        return Boolean.TRUE.equals(namedJdbcTemplate.queryForObject(sql, params, Boolean.class));
    }

    public Optional<Long> findIdByUserName(final String name) {
        final String sql = "SELECT id FROM customer WHERE name = :name";
        final HashMap<String, Object> params = new HashMap<>();
        params.put("name", name.toLowerCase());

        List<Long> customerIds = namedJdbcTemplate.query(sql, params, (rs, rowNum) -> rs.getLong("id"));
        return Optional.ofNullable(DataAccessUtils.singleResult(customerIds));
    }

    public Optional<Customer> findByEmail(final String email) {
        final String sql = "SELECT id, name, email, password FROM customer WHERE email = :email";
        final HashMap<String, Object> params = new HashMap<>();
        params.put("email", email);

        return findCustomer(sql, params);
    }

    public Optional<Customer> findById(final Long id) {
        final String sql = "SELECT id, name, email, password FROM customer WHERE id = :id";
        final HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);

        return findCustomer(sql, params);
    }

    private Optional<Customer> findCustomer(final String sql, final HashMap<String, Object> params) {
        try {
            return Optional.ofNullable(namedJdbcTemplate.queryForObject(sql, params, CustomerDao::rowMapper));
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public int update(final Customer customer) {
        final String sql = "UPDATE customer SET name = :name, password = :password WHERE id = :id";
        final HashMap<String, Object> params = new HashMap<>();
        params.put("id", customer.getId());
        params.put("name", customer.getName());
        params.put("password", customer.getPassword().getHashedValue());

        return namedJdbcTemplate.update(sql, params);
    }

    public int deleteById(final Long id) {
        final String sql = "DELETE FROM customer WHERE id = :id";
        final HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);

        return namedJdbcTemplate.update(sql, params);
    }
}

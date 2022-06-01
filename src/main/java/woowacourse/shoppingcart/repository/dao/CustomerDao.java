package woowacourse.shoppingcart.repository.dao;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Repository
public class CustomerDao {

    public static final String REAL_CUSTOMER_QUERY = " (select id, username, password, nickname, withdrawal from customer where withdrawal = false) ";
    private static final RowMapper<Customer> ROW_MAPPER = (resultSet, rowNum) -> new Customer(
            resultSet.getLong("id"),
            resultSet.getString("username"),
            resultSet.getString("password"),
            resultSet.getString("nickname"),
            resultSet.getBoolean("withdrawal")
    );

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CustomerDao(final JdbcTemplate jdbcTemplate,
                       final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Long findIdByUserName(final String userName) {
        try {
            final String query = "SELECT id FROM customer WHERE username = ?";
            return jdbcTemplate.queryForObject(query, Long.class, userName.toLowerCase(Locale.ROOT));
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public Long create(final Customer customer) {
        String query = "insert into customer (username, password, nickname, withdrawal)"
                + " values (:username, :password, :nickname, :withdrawal)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource source = new BeanPropertySqlParameterSource(customer);
        namedParameterJdbcTemplate.update(query, source, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public Customer findById(final Long id) {
        String query = "select id, username, password, nickname, withdrawal from"
                + REAL_CUSTOMER_QUERY
                + "where id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        try {
            return namedParameterJdbcTemplate.queryForObject(query, params, ROW_MAPPER);
        } catch (EmptyResultDataAccessException exception) {
            throw new InvalidCustomerException();
        }
    }

    public Customer login(final String username, final String password) {
        String query = "select id, username, password, nickname, withdrawal from"
                + REAL_CUSTOMER_QUERY
                + "where username = :username and password = :password";
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        try {
            return namedParameterJdbcTemplate.queryForObject(query, params, ROW_MAPPER);
        } catch (EmptyResultDataAccessException exception) {
            throw new InvalidCustomerException();
        }
    }
}

package woowacourse.shoppingcart.dao;

import java.util.Locale;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Repository
public class CustomerDao {

    private static final String TABLE_NAME = "customer";
    private static final String KEY_NAME = "id";

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public CustomerDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns(KEY_NAME);
    }

    public Customer save(Customer customer) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(customer);
        Long id = simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();

        return new Customer(id, customer);
    }

    public Long findIdByUsername(String username) {
        try {
            String sql = "SELECT id FROM customer WHERE username = ?";
            return jdbcTemplate.queryForObject(sql, Long.class, username.toLowerCase(Locale.ROOT));
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public Optional<Customer> findByUsername(String username) {
        try {
            String sql = "SELECT id, username, email, password, address, phone_number "
                    + "FROM customer WHERE username = :username";
            SqlParameterSource parameterSource = new MapSqlParameterSource("username", username);
            return Optional.ofNullable(
                    namedParameterJdbcTemplate.queryForObject(sql, parameterSource, generateCustomerMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private RowMapper<Customer> generateCustomerMapper() {
        return (resultSet, rowNum) ->
                new Customer(
                        resultSet.getLong("id"),
                        resultSet.getString("username"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("address"),
                        resultSet.getString("phone_number")
                );
    }

    public void update(Customer customer) {
        String sql = "UPDATE customer SET address = :address, phone_number = :phoneNumber WHERE username = :username";
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(customer);
        namedParameterJdbcTemplate.update(sql, parameterSource);
    }

    public void delete(Customer customer) {
        String sql = "DELETE FROM customer WHERE username = :username";
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(customer);
        namedParameterJdbcTemplate.update(sql, parameterSource);
    }
}

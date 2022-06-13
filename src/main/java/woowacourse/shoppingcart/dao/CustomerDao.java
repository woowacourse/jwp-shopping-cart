package woowacourse.shoppingcart.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.UserName;
import woowacourse.shoppingcart.exception.InvalidCustomerException;
import woowacourse.shoppingcart.exception.InvalidProductException;

import java.util.Locale;

@Repository
public class CustomerDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CustomerDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void save(Customer customer) {
        final String query = "INSERT INTO customer (username, password) VALUES (:username, :password)";
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("username", customer.getName().value());
        parameterSource.addValue("password", customer.getPassword().value());
        namedParameterJdbcTemplate.update(query, parameterSource, keyHolder);
    }

    public Long findIdByUserName(final UserName userName) {
        try {
            final String query = "SELECT id FROM customer WHERE username = :username";
            MapSqlParameterSource parameterSource = new MapSqlParameterSource("username", userName.value().toLowerCase(Locale.ROOT));
            return namedParameterJdbcTemplate.queryForObject(query, parameterSource, Long.class);
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public void deleteByName(UserName customerName) {
        final String query = "DELETE FROM customer WHERE username = :username";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("username", customerName.value());
        namedParameterJdbcTemplate.update(query, parameterSource);
    }

    public Customer findCustomerByName(UserName userName) {
        try {
            final String query = "SELECT id, username, password FROM customer WHERE username = :username";
            MapSqlParameterSource parameterSource = new MapSqlParameterSource("username", userName.value());
            return namedParameterJdbcTemplate.queryForObject(query, parameterSource, rowMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidProductException();
        }
    }

    private RowMapper<Customer> rowMapper() {
        return (resultSet, rowNumber) -> new Customer(
                resultSet.getLong("id"),
                resultSet.getString("username"),
                resultSet.getString("password")
        );
    }

    public void updateByName(UserName customerName, Customer customer) {
        final String query = "UPDATE customer SET password = :password WHERE username = :username";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("username", customer.getName().value());
        parameterSource.addValue("password", customer.getPassword().value());
        namedParameterJdbcTemplate.update(query, parameterSource);
    }

    public boolean checkNotExistsCustomer(Customer customer) {
        final String query = "SELECT EXISTS (SELECT id FROM customer where username = :username and password = :password)";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("username", customer.getName().value());
        parameterSource.addValue("password", customer.getPassword().value());
        return !namedParameterJdbcTemplate.queryForObject(query, parameterSource, Boolean.class);
    }

    public boolean existsByName(UserName userName) {
        final String query = "SELECT EXISTS (SELECT id FROM customer where username = :username)";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("username", userName.value());
        return namedParameterJdbcTemplate.queryForObject(query, parameterSource, Boolean.class);
    }
}

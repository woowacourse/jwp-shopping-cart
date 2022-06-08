package woowacourse.shoppingcart.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.exception.InvalidCustomerException;
import woowacourse.shoppingcart.exception.InvalidProductException;

import java.sql.PreparedStatement;
import java.util.Locale;

@Repository
public class CustomerDao {

    private final JdbcTemplate jdbcTemplate;

    public CustomerDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Customer customer) {
        final String query = "INSERT INTO customer (username, password) VALUES (?, ?)";
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement preparedStatement =
                    connection.prepareStatement(query, new String[]{"id"});
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getEncryptedPassword());
            return preparedStatement;
        }, keyHolder);
    }

    public Long findIdByUserName(final String userName) {
        try {
            final String query = "SELECT id FROM customer WHERE username = ?";
            return jdbcTemplate.queryForObject(query, Long.class, userName.toLowerCase(Locale.ROOT));
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public void deleteByName(String userName) {
        final String query = "DELETE FROM customer WHERE username = ?";
        jdbcTemplate.update(query, userName);
    }

    public Customer findCustomerByName(String userName) {
        try {
            final String query = "SELECT id, password FROM customer WHERE username = ?";
            return jdbcTemplate.queryForObject(query, (resultSet, rowNumber) ->
                    Customer.of(
                            resultSet.getLong("id"),
                            userName,
                            resultSet.getString("password")
                    ), userName
            );
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidProductException();
        }
    }

    public void updateCustomer(Customer customer) {
        final String query = "UPDATE customer SET password = ? WHERE username = ?";
        jdbcTemplate.update(query, customer.getEncryptedPassword(), customer.getName());
    }

    public boolean existsByName(String name) {
        final String query = "SELECT EXISTS (SELECT id FROM customer where username = ?)";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(query, Boolean.class, name));
    }

    public boolean existsCustomer(Customer customer) {
        final String query = "SELECT EXISTS (SELECT id FROM customer where username = ? and password = ?)";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(query, Boolean.class, customer.getName(), customer.getEncryptedPassword()));
    }
}

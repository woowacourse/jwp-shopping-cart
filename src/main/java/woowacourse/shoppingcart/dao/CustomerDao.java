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
            preparedStatement.setString(1, customer.getName().toString());
            preparedStatement.setString(2, customer.getPassword().toString());
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

    public void deleteByName(String customerName) {
        final String query = "DELETE FROM customer WHERE username = ?";
        jdbcTemplate.update(query, customerName);
    }

    public Customer findCustomerByName(String customerName) {
        try {
            final String query = "SELECT id, password FROM customer WHERE username = ?";
            return jdbcTemplate.queryForObject(query, (resultSet, rowNumber) ->
                    new Customer(
                            resultSet.getLong("id"),
                            customerName,
                            resultSet.getString("password")
                    ), customerName
            );
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidProductException();
        }
    }

    public void updateByName(String customerName, Customer customer) {
        final String query = "UPDATE customer SET password = ? WHERE username = ?";
        jdbcTemplate.update(query, customer.getPassword().toString(), customerName);
    }

    public boolean existsCustomer(Customer customer) {
        final String query = "SELECT EXISTS (SELECT id FROM customer where username = ? and password = ?)";
        return Boolean.TRUE.equals(
                jdbcTemplate.queryForObject(
                        query,
                        Boolean.class,
                        customer.getName().toString(),
                        customer.getPassword().toString()
                )
        );
    }

    public boolean existsByName(String name) {
        final String query = "SELECT EXISTS (SELECT id FROM customer where username = ?)";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(query, Boolean.class, name));
    }
}

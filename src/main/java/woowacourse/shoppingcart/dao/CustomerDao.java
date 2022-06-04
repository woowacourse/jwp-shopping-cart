package woowacourse.shoppingcart.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.UserName;
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
            preparedStatement.setString(1, customer.getName().value());
            preparedStatement.setString(2, customer.getPassword().value());
            return preparedStatement;
        }, keyHolder);
    }

    public Long findIdByUserName(final UserName userName) {
        try {
            final String query = "SELECT id FROM customer WHERE username = ?";
            return jdbcTemplate.queryForObject(query, Long.class, userName.value().toLowerCase(Locale.ROOT));
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public void deleteByName(UserName customerName) {
        final String query = "DELETE FROM customer WHERE username = ?";
        jdbcTemplate.update(query, customerName.value());
    }

    public Customer findCustomerByName(UserName customerName) {
        try {
            final String query = "SELECT id, password FROM customer WHERE username = ?";
            return jdbcTemplate.queryForObject(query, (resultSet, rowNumber) ->
                    new Customer(
                            resultSet.getLong("id"),
                            customerName.value(),
                            resultSet.getString("password")
                    ), customerName.value()
            );
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidProductException();
        }
    }

    public void updateByName(UserName customerName, Customer customer) {
        final String query = "UPDATE customer SET password = ? WHERE username = ?";
        jdbcTemplate.update(query, customer.getPassword().value(), customerName.value());
    }

    public boolean existsCustomer(Customer customer) {
        final String query = "SELECT EXISTS (SELECT id FROM customer where username = ? and password = ?)";
        return Boolean.TRUE.equals(
                jdbcTemplate.queryForObject(
                        query,
                        Boolean.class,
                        customer.getName().value(),
                        customer.getPassword().value()
                )
        );
    }

    public boolean existsByName(UserName userName) {
        final String query = "SELECT EXISTS (SELECT id FROM customer where username = ?)";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(query, Boolean.class, userName.value()));
    }
}

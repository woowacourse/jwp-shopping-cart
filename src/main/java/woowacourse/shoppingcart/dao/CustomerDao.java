package woowacourse.shoppingcart.dao;

import java.util.Locale;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Repository
public class CustomerDao {

    private static final RowMapper<Customer> CUSTOMER_MAPPER = (rs, rowNum) -> {
        var username = rs.getString("username");
        var email = rs.getString("email");
        var password = rs.getString("password");
        return new Customer(username, email, password);
    };
    private final JdbcTemplate jdbcTemplate;

    public CustomerDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long findIdByUserName(final String userName) {
        try {
            final String query = "SELECT id FROM customer WHERE username = ?";
            return jdbcTemplate.queryForObject(query, Long.class, userName.toLowerCase(Locale.ROOT));
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public Customer findCustomerByUserName(final String userName) {
        try {
            final String query = "SELECT * FROM customer WHERE username = ?";
            return jdbcTemplate.queryForObject(query, CUSTOMER_MAPPER, userName.toLowerCase(Locale.ROOT));
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public boolean isValidName(String username) {
        final var sql = "SELECT * FROM customer WHERE exists (SELECT username FROM customer WHERE username = ?)";
        return jdbcTemplate.query(sql, CUSTOMER_MAPPER, username).size() > 0;
    }

    public boolean isValidEmail(String email) {
        final var sql = "SELECT * FROM customer WHERE exists (SELECT username FROM customer WHERE email = ?)";
        return jdbcTemplate.query(sql, CUSTOMER_MAPPER, email).size() > 0;
    }

    public void updatePassword(String name, String newPassword) {
        final String sql = "UPDATE customer SET password = (?) WHERE username = (?)";
        jdbcTemplate.update(sql, newPassword, name);
    }

    public void deleteByName(String name) {
        final var sql = "DELETE FROM customer WHERE username = ?";
        jdbcTemplate.update(sql, name);
    }

    public void saveCustomer(String name, String email, String password) {
        final String sql = "INSERT INTO customer (username, email, password) VALUES(?, ?, ?)";
        jdbcTemplate.update(sql, name, email, password);
    }
}

package woowacourse.shoppingcart.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Repository
public class CustomerDao {

    private static final RowMapper<Customer> CUSTOMER_MAPPER = (rs, rowNum) -> {
        var id = rs.getLong("id");
        var username = rs.getString("username");
        var email = rs.getString("email");
        var password = rs.getString("password");
        return new Customer(id, username, email, password);
    };
    public static final String NOT_EXIST_NAME = "[ERROR] 존재하지 않는 이름입니다.";
    private static final String NOT_EXIST_EMAIL = "[ERROR] 존재하지 않는 이메일입니다.";
    private final JdbcTemplate jdbcTemplate;

    public CustomerDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long findIdByUserName(String userName) {
        try {
            final String query = "SELECT id FROM customer WHERE username = ?";
            return jdbcTemplate.queryForObject(query, Long.class, userName);
        }
        catch (EmptyResultDataAccessException e) {
            throw new InvalidCustomerException(NOT_EXIST_NAME);
        }

    }

    public Customer findCustomerByUserName(String userName) {
        try {
            final String query = "SELECT * FROM customer WHERE username = ?";
            return jdbcTemplate.queryForObject(query, CUSTOMER_MAPPER, userName);
        }
        catch (EmptyResultDataAccessException e) {
            throw new InvalidCustomerException(NOT_EXIST_NAME);
        }
    }

    public boolean isValidName(String userName) {
        final var sql = "SELECT * FROM customer WHERE exists (SELECT username FROM customer WHERE username = ?)";
        return jdbcTemplate.query(sql, CUSTOMER_MAPPER, userName).size() > 0;
    }

    public boolean isValidEmail(String email) {
        final var sql = "SELECT * FROM customer WHERE exists (SELECT username FROM customer WHERE email = ?)";
        return jdbcTemplate.query(sql, CUSTOMER_MAPPER, email).size() > 0;
    }

    public void updatePassword(String username, String newPassword) {
        final String sql = "UPDATE customer SET password = (?) WHERE username = (?)";
        jdbcTemplate.update(sql, newPassword, username);
    }

    public void deleteByName(String username) {
        final var sql = "DELETE FROM customer WHERE username = ?";
        jdbcTemplate.update(sql, username);
    }

    public void saveCustomer(String username, String email, String password) {
        final String sql = "INSERT INTO customer (username, email, password) VALUES(?, ?, ?)";
        jdbcTemplate.update(sql, username, email, password);
    }

    public Customer findCustomerByEmail(String email) {
        try {
            final String query = "SELECT * FROM customer WHERE email = ?";
            return jdbcTemplate.queryForObject(query, CUSTOMER_MAPPER, email);
        }
        catch (EmptyResultDataAccessException e) {
            throw new InvalidCustomerException(NOT_EXIST_EMAIL);
        }
    }
}

package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.Locale;
import java.util.Objects;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.values.password.EncryptedPassword;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Repository
public class CustomerDao {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Customer> customerRowMapper = ((rs, rowNum) ->
            Customer.builder()
                    .id(rs.getLong("id"))
                    .username(rs.getString("username"))
                    .password(rs.getString("password"))
                    .phoneNumber(rs.getString("phone_number"))
                    .address(rs.getString("address"))
                    .build()
    );

    public CustomerDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Customer findById(final Long id) {
        try {
            final String query = "SELECT id, username, password, phone_number, address FROM customer WHERE id = ?";
            return jdbcTemplate.queryForObject(query, customerRowMapper, id);
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public Long findIdByUserName(final String username) {
        try {
            final String query = "SELECT id FROM customer WHERE username = ?";
            return jdbcTemplate.queryForObject(query, Long.class, username.toLowerCase(Locale.ROOT));
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public Customer findByUsername(final String username) {
        try {
            final String query = "SELECT id, username, password, phone_number, address FROM customer WHERE username = ?";
            return jdbcTemplate.queryForObject(query, customerRowMapper, username);
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public Long save(final Customer customer) {
        final String query = "INSERT INTO customer (username, password, phone_number, address) VALUES (?, ?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(query, new String[]{"id"});
            preparedStatement.setString(1, customer.getUsername());
            preparedStatement.setString(2, customer.getPassword());
            preparedStatement.setString(3, customer.getPhoneNumber());
            preparedStatement.setString(4, customer.getAddress());
            return preparedStatement;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public boolean existCustomerByUsername(final String username) {
        final String query = "SELECT EXISTS (SELECT id FROM customer WHERE username = ?)";
        return jdbcTemplate.queryForObject(query, Boolean.class, username);
    }

    public void update(final Customer customer) {
        final String query = "UPDATE customer SET phone_number = ?, address = ? WHERE id = ?";
        int rowCount = jdbcTemplate.update(query, customer.getPhoneNumber(), customer.getAddress(),
                customer.getId());
        if (rowCount == 0) {
            throw new InvalidCustomerException();
        }
    }

    public void updatePasswordById(final Long customerId, final EncryptedPassword password) {
        final String query = "UPDATE customer SET password = ? WHERE id = ?";
        int rowCount = jdbcTemplate.update(query, password.getPassword(), customerId);
        if (rowCount == 0) {
            throw new InvalidCustomerException();
        }
    }

    public int deleteById(final Long customerId) {
        final String query = "DELETE FROM customer WHERE id = ?";
        return jdbcTemplate.update(query, customerId);
    }
}

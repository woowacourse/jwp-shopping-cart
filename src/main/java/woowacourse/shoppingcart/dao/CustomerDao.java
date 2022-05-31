package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.Locale;
import java.util.Objects;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.exception.InvalidCustomerException;
import woowacourse.shoppingcart.exception.NotFoundCustomerException;

@Repository
public class CustomerDao {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Customer> rowMapper = (rs, rowNum) ->
            new Customer(
                    rs.getLong("id"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("password"));

    public CustomerDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(Customer customer) {
        final String query = "INSERT INTO customer (username, email, password) VALUES (?, ?, ?)";
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement preparedStatement =
                    connection.prepareStatement(query, new String[]{"id"});
            preparedStatement.setString(1, customer.getUsername());
            preparedStatement.setString(2, customer.getEmail());
            preparedStatement.setString(3, customer.getPassword());
            return preparedStatement;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public Long findIdByUserName(final String userName) {
        try {
            final String query = "SELECT id FROM customer WHERE username = ?";
            return jdbcTemplate.queryForObject(query, Long.class, userName.toLowerCase(Locale.ROOT));
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public boolean existEmail(String email) {
        final String query = "SELECT EXISTS(SELECT * FROM customer WHERE email = ?)";
        return jdbcTemplate.queryForObject(query, Boolean.class, email);
    }

    public Customer findByEmail(String email) {
        try {
            final String query = "SELECT * FROM customer WHERE email = ?";
            return jdbcTemplate.queryForObject(query, rowMapper, email);
        } catch (EmptyResultDataAccessException exception) {
            throw new NotFoundCustomerException();
        }
    }

    public void updateById(Long id, Customer updatedCustomer) {
        final String query = "UPDATE customer SET username = ?, password = ? WHERE id = ?";
        jdbcTemplate.update(query, updatedCustomer.getUsername(), updatedCustomer.getPassword(), id);
    }

    public void deleteById(Long id) {
        final String query = "DELETE FROM customer WHERE id = ?";
        jdbcTemplate.update(query, id);
    }
}

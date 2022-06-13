package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.EncryptPassword;
import woowacourse.shoppingcart.domain.customer.UserName;
import woowacourse.shoppingcart.exception.CannotDeleteCustomerException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Repository
public class CustomerDao {

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

    public Long save(final String userName, final String password) {
        final String query = "INSERT INTO customer (username, password) VALUES (?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(query, new String[]{"id"});
            statement.setString(1, userName.toLowerCase(Locale.ROOT));
            statement.setString(2, password);
            return statement;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public boolean existsByUserName(final String userName) {
        final String query = "SELECT id, username, password FROM customer WHERE username = ? LIMIT 1";
        List<Customer> customers = jdbcTemplate.query(query, rowMapper(), userName.toLowerCase(Locale.ROOT));
        return !customers.isEmpty();
    }

    private RowMapper<Customer> rowMapper() {
        return (rs, rowNum) -> {
            Long id = rs.getLong("id");
            String userName = rs.getString("username");
            String password = rs.getString("password");
            return new Customer(id, new UserName(userName), new EncryptPassword(password));
        };
    }

    public Optional<Customer> findById(final Long id) {
        final String query = "SELECT id, username, password FROM customer WHERE id = ?";
        final List<Customer> customers = jdbcTemplate.query(query, rowMapper(), id);
        return Optional.ofNullable(DataAccessUtils.singleResult(customers));
    }

    public Optional<Customer> findByUserName(final String userName) {
        final String query = "SELECT id, username, password FROM customer WHERE username = ?";
        final List<Customer> customers = jdbcTemplate.query(query, rowMapper(), userName);
        return Optional.ofNullable(DataAccessUtils.singleResult(customers));
    }

    public Customer update(final Long id, final String userName, final String password) {
        final String query = "UPDATE customer SET userName = ?, password = ? WHERE id = ?";
        final int update = jdbcTemplate.update(query, userName, password, id);
        if (update == 0) {
            throw new InvalidCustomerException();
        }
        return new Customer(id, new UserName(userName), new EncryptPassword(password));
    }

    public void deleteById(final Long id) {
        final String query = "DELETE FROM customer WHERE id = ?";
        final int update = jdbcTemplate.update(query, id);
        if (update == 0) {
            throw new CannotDeleteCustomerException();
        }
    }
}

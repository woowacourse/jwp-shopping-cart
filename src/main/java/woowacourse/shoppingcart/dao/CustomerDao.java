package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Locale;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Customer;
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
            return new Customer(id, userName, password);
        };
    }
}

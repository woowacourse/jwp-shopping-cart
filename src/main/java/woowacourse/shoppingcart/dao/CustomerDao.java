package woowacourse.shoppingcart.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Repository
public class CustomerDao {
    private static final RowMapper<Customer> CUSTOMER_ROW_MAPPER = (resultSet, rowNum) -> Customer.of(
            resultSet.getString("username"),
            resultSet.getString("password"),
            resultSet.getString("nickname"),
            resultSet.getInt("age")
    );

    private final JdbcTemplate jdbcTemplate;

    public CustomerDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(Customer customer) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO customer (username, password, nickname, age) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, customer.getUserName());
            ps.setString(2, customer.getPassword());
            ps.setString(3, customer.getNickName());
            ps.setInt(4, customer.getAge());
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public Long getIdByUserName(final String userName) {
        try {
            final String query = "SELECT id FROM customer WHERE username = ?";
            return jdbcTemplate.queryForObject(query, Long.class, userName.toLowerCase(Locale.ROOT));
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public List<String> findAllUserNames() {
        String sql = "SELECT username FROM customer";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    public boolean isUsernameExist(String userName) {
        String sql = "SELECT EXISTS (SELECT username FROM customer WHERE username = ?) AS isExist;";
        return jdbcTemplate.queryForObject(sql, Boolean.class, userName);
    }

    public Customer getCustomerByUserName(String userName) {
        try {
            final String sql = "SELECT username, password, nickname, age FROM customer WHERE username = ?";
            return jdbcTemplate.queryForObject(sql, CUSTOMER_ROW_MAPPER, userName);
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public void updatePassword(Customer customer) {
        final String sql = "UPDATE customer SET password = ? WHERE username = ?";
        int updated = jdbcTemplate.update(sql, customer.getPassword(), customer.getUserName());
        validateUpdated(updated);
    }

    public void updateInfo(Customer customer) {
        final String sql = "UPDATE customer SET nickname = ?, age = ? WHERE username = ?";
        int updated = jdbcTemplate.update(sql, customer.getNickName(), customer.getAge(), customer.getUserName());
        validateUpdated(updated);
    }

    private void validateUpdated(int updated) {
        if (updated == 0) {
            throw new InvalidCustomerException();
        }
    }

    public void delete(Customer customer) {
        final String sql = "DELETE FROM customer WHERE username = ?";
        jdbcTemplate.update(sql, customer.getUserName());
    }
}

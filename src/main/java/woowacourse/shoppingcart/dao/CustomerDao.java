package woowacourse.shoppingcart.dao;

import java.util.Locale;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Email;
import woowacourse.shoppingcart.domain.Password;
import woowacourse.shoppingcart.domain.Username;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Repository
public class CustomerDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private RowMapper<Customer> customerRowMapper = (rs, rowNum) -> new Customer(
            rs.getLong("id"),
            new Username(rs.getString("username")),
            new Email(rs.getString("email")),
            new Password(rs.getString("password"))
    );

    public CustomerDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("customer")
                .usingGeneratedKeyColumns("id");
    }

    public Customer save(Customer customer) {
        String username = customer.getUsername().getValue();
        String email = customer.getEmail().getValue();
        String password = customer.getPassword().getValue();

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("username", username.toLowerCase(Locale.ROOT))
                .addValue("email", email)
                .addValue("password", password);

        long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return new Customer(id, new Username(username), new Email(email), new Password(password));
    }

    public Customer findByUsername(Username username) {
        try {
            final String query = "SELECT id, username, email, password FROM customer WHERE username = ?";
            return jdbcTemplate.queryForObject(query, customerRowMapper, username.getValue().toLowerCase(Locale.ROOT));
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public Customer findByEmail(Email email) {
        try {
            final String query = "SELECT id, username, email, password FROM customer WHERE email = ?";
            return jdbcTemplate.queryForObject(query, customerRowMapper, email.getValue());
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public boolean existByUsername(Username username) {
        try {
            String query = "SELECT EXISTS (SELECT * FROM customer WHERE username = ?)";
            return jdbcTemplate.queryForObject(query, Boolean.class, username.getValue());
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public boolean existByEmail(Email email) {
        try {
            String query = "SELECT EXISTS (SELECT * FROM customer WHERE email = ?)";
            return jdbcTemplate.queryForObject(query, Boolean.class, email.getValue());
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public void updatePassword(Long id, Password password) {
        try {
            final String query = "UPDATE customer SET password = ? WHERE id = ?";
            jdbcTemplate.update(query, password.getValue(), id);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public void deleteByUsername(Username username) {
        try {
            final String query = "DELETE FROM customer WHERE username = ?";
            jdbcTemplate.update(query, username.getValue());
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }
}

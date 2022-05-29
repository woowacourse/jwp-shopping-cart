package woowacourse.shoppingcart.dao;

import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.auth.domain.Customer;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

import java.util.Locale;

@Repository
public class CustomerDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public CustomerDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("CUSTOMER")
                .usingGeneratedKeyColumns("id");
    }

    Customer save(Customer customer) {
        String username = customer.getUsername();
        String email = customer.getEmail();
        String password = customer.getPassword();

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("username", username)
                .addValue("email", email)
                .addValue("password", password);

        long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return new Customer(id, username, email, password);
    }

    public Long findIdByUserName(final String userName) {
        try {
            final String query = "SELECT id FROM customer WHERE username = ?";
            return jdbcTemplate.queryForObject(query, Long.class, userName.toLowerCase(Locale.ROOT));
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }
}

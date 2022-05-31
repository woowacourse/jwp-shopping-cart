package woowacourse.shoppingcart.dao;

import java.util.Locale;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Repository
public class CustomerDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public CustomerDao(final JdbcTemplate jdbcTemplate,
                       final DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("CUSTOMER")
                .usingGeneratedKeyColumns("id");
    }

    public Long save(Customer customer) {
        SqlParameterSource parameter = new BeanPropertySqlParameterSource(customer);
        return simpleJdbcInsert.executeAndReturnKey(parameter).longValue();
    }

    public Long findIdByName(final String userName) {
        try {
            final String query = "SELECT id FROM customer WHERE name = ?";
            return jdbcTemplate.queryForObject(query, Long.class, userName.toLowerCase(Locale.ROOT));
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public boolean existEmail(final String email) {
        final String query = "SELECT EXISTS (SELECT * FROM customer WHERE email = ?)";
        return jdbcTemplate.queryForObject(query, Boolean.class, email);
    }
}

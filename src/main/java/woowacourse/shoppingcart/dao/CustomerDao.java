package woowacourse.shoppingcart.dao;

import java.util.Locale;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
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
}

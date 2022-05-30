package woowacourse.shoppingcart.dao;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
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

    public void save(Customer customer) {
        final SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("customer")
                .usingGeneratedKeyColumns("id");
        Map<String, Object> params = new HashMap<>();
        params.put("email", customer.getEmail());
        params.put("username", customer.getUsername());
        params.put("password", customer.getPassword());

        simpleJdbcInsert.execute(params);
    }
}

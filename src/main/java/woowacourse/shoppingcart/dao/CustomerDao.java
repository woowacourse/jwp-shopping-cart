package woowacourse.shoppingcart.dao;

import java.util.Locale;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Repository
public class CustomerDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public CustomerDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("customer")
            .usingGeneratedKeyColumns("id");
    }

    public Long findIdByUserName(final String userName) {
        try {
            final String query = "SELECT id FROM customer WHERE username = ?";
            return jdbcTemplate.queryForObject(query, Long.class, userName.toLowerCase(Locale.ROOT));
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public Customer save(Customer customer) {
        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("username", customer.getUsername().getValue())
            .addValue("password", customer.getPassword().getValue())
            .addValue("phoneNumber", customer.getPhoneNumber().getValue())
            .addValue("address", customer.getAddress());
        Number newId = jdbcInsert.executeAndReturnKey(params);

        return new Customer(
            newId.longValue(),
            customer.getUsername(),
            customer.getPassword(),
            customer.getPhoneNumber(),
            customer.getAddress()
        );
    }
}

package woowacourse.shoppingcart.dao;

import java.util.Locale;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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

    private final RowMapper<Customer> customerRowMapper = (resultSet, rowNum) -> new Customer(
            resultSet.getString("email"),
            resultSet.getString("password"),
            resultSet.getString("name"),
            resultSet.getString("phone"),
            resultSet.getString("address")
    );

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

    public Optional<Long> findIdByEmailAndPassword(String email, String password) {
        String query = "SELECT id FROM CUSTOMER WHERE email = ? AND password = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, Long.class, email, password));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Customer findCustomerById(Long customerId) {
        String query = "SELECT * FROM CUSTOMER WHERE id = ?";
        return jdbcTemplate.queryForObject(query, customerRowMapper, customerId);
    }

    public boolean existId(Long customerId) {
        final String query = "SELECT EXISTS (SELECT * FROM customer WHERE id = ?)";
        return jdbcTemplate.queryForObject(query, Boolean.class, customerId);
    }
}

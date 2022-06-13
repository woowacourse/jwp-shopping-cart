package woowacourse.shoppingcart.dao;

import java.util.Locale;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Email;
import woowacourse.shoppingcart.domain.Password;
import woowacourse.exception.badRequest.InvalidCustomerException;

@Repository
public class CustomerDao {
    private static final RowMapper<Customer> CUSTOMER_ROW_MAPPER = (rs, rowNum) -> {
        return new Customer(
                rs.getLong("id"),
                new Email(rs.getString("email")),
                rs.getString("name"),
                Password.encodePassword(rs.getString("password"))
        );
    };

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public CustomerDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("customer")
                .usingGeneratedKeyColumns("id");
    }

    public Long findIdByUserName(final String name) {
        try {
            final String query = "SELECT id FROM customer WHERE name = ?";
            return jdbcTemplate.queryForObject(query, Long.class, name.toLowerCase(Locale.ROOT));
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public Optional<Customer> findById(Long id) {
        final String sql = "SELECT id, email, name, password FROM customer WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, CUSTOMER_ROW_MAPPER, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Long save(Customer customer) {
        final Number number = simpleJdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(customer));
        return number.longValue();
    }

    public Optional<Customer> findByEmail(String email) {
        final String sql = "SELECT id, email, name, password FROM customer where email = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, CUSTOMER_ROW_MAPPER, email));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void updateProfile(Customer customer) {
        final String sql = "UPDATE customer SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, customer.getName(), customer.getId());
    }

    public void updatePassword(Customer customer) {
        final String sql = "UPDATE customer SET password = ? WHERE id = ?";
        jdbcTemplate.update(sql, customer.getPassword(), customer.getId());
    }

    public void delete(long id) {
        final String sql = "DELETE FROM customer WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}

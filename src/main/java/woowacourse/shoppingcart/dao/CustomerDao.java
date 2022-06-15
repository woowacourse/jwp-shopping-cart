package woowacourse.shoppingcart.dao;

import java.util.Locale;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.Password;
import woowacourse.shoppingcart.domain.customer.UserName;
import woowacourse.shoppingcart.exception.notfound.NotFoundCustomerException;

@Repository
public class CustomerDao {

    private static final RowMapper<Customer> ROW_MAPPER = (resultSet, rowNum) -> new Customer(
            resultSet.getLong("id"),
            resultSet.getString("userName"),
            resultSet.getString("password"));

    private final SimpleJdbcInsert inserter;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CustomerDao(final DataSource dataSource) {
        this.inserter = new SimpleJdbcInsert(dataSource)
                .withTableName("customer")
                .usingGeneratedKeyColumns("id");
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public Long save(final Customer customer) {
        final SqlParameterSource parameters = new BeanPropertySqlParameterSource(customer);
        return inserter.executeAndReturnKey(parameters).longValue();
    }

    public Customer getByName(final UserName userName) {
        try {
            final String query = "SELECT * FROM customer WHERE username = :userName";
            return jdbcTemplate.queryForObject(query, Map.of("userName", userName.getValue()), ROW_MAPPER);
        } catch (final EmptyResultDataAccessException e) {
            throw new NotFoundCustomerException();
        }
    }

    public Long getIdByUserName(final UserName userName) {
        try {
            final String query = "SELECT id FROM customer WHERE username = :userName";
            return jdbcTemplate.queryForObject(query, Map.of("userName", userName.getValue().toLowerCase(Locale.ROOT)),
                    Long.class);
        } catch (final EmptyResultDataAccessException e) {
            throw new NotFoundCustomerException();
        }
    }

    public void updatePasswordByName(final UserName userName, final Password newPassword) {
        final String query = "UPDATE customer SET password = :newPassword WHERE username = :userName";
        jdbcTemplate.update(query,
                Map.of("newPassword", newPassword.getValue(), "userName", userName.getValue()));
    }

    public void deleteByName(final UserName userName) {
        final String query = "DELETE FROM customer WHERE username = :userName";
        jdbcTemplate.update(query, Map.of("userName", userName.getValue()));
    }

    public boolean existsByNameAndPassword(final UserName userName, final Password password) {
        final String query = "SELECT EXISTS (SELECT id FROM customer where username = :userName and password = :password)";
        return Boolean.TRUE.equals(
                jdbcTemplate.queryForObject(query,
                        Map.of("userName", userName.getValue(), "password", password.getValue()), Boolean.class));
    }

    public boolean existsByName(final UserName userName) {
        final String query = "SELECT EXISTS (SELECT id FROM customer where username = :userName)";
        return Boolean.TRUE.equals(
                jdbcTemplate.queryForObject(query, Map.of("userName", userName.getValue()), Boolean.class));
    }
}

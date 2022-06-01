package woowacourse.shoppingcart.dao;

import java.util.Map;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

import java.util.Locale;

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

    public Customer getByName(final String name) {
        try {
            final String query = "SELECT * FROM customer WHERE username = :name";
            return jdbcTemplate.queryForObject(query, Map.of("name", name), ROW_MAPPER);
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public Long getIdByUserName(final String name) {
        try {
            final String query = "SELECT id FROM customer WHERE username = :name";
            return jdbcTemplate.queryForObject(query, Map.of("name", name.toLowerCase(Locale.ROOT)), Long.class);
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public void updatePasswordByName(final String name, final String newPassword) {
        final String query = "UPDATE customer SET password = :newPassword WHERE username = :name";
        jdbcTemplate.update(query, Map.of("newPassword", newPassword, "name", name));
    }

    public void deleteByName(final String name) {
        final String query = "DELETE FROM customer WHERE username = :name";
        jdbcTemplate.update(query, Map.of("name", name));
    }

    public boolean existsByNameAndPassword(final String name, final String password) {
        final String query = "SELECT EXISTS (SELECT id FROM customer where username = :name and password = :password)";
        return Boolean.TRUE.equals(
                jdbcTemplate.queryForObject(query, Map.of("name", name, "password", password), Boolean.class));
    }

    public boolean existsByName(final String name) {
        final String query = "SELECT EXISTS (SELECT id FROM customer where username = :name)";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(query, Map.of("name", name), Boolean.class));
    }
}

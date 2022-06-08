package woowacourse.customer.dao;

import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import woowacourse.customer.domain.Customer;

@Repository
public class CustomerDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public CustomerDao(final DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("customer")
            .usingGeneratedKeyColumns("id");
    }

    public Long findIdByUsername(final String username) {
        final String sql = "SELECT id FROM customer WHERE username = :username";
        final SqlParameterSource params = new MapSqlParameterSource("username", username);

        return jdbcTemplate.queryForObject(sql, params, Long.class);
    }

    public boolean existsByUsername(final String username) {
        final String sql = "SELECT EXISTS (SELECT * FROM customer WHERE username = :username)";
        final SqlParameterSource params = new MapSqlParameterSource("username", username);

        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, params, Boolean.class));
    }

    public Customer save(final Customer customer) {
        final SqlParameterSource params = new MapSqlParameterSource()
            .addValue("username", customer.getUsername().getValue())
            .addValue("password", customer.getPassword().getValue())
            .addValue("phoneNumber", customer.getPhoneNumber().getValue())
            .addValue("address", customer.getAddress());
        final Long id = jdbcInsert.executeAndReturnKey(params).longValue();

        return new Customer(
            id, customer.getUsername(), customer.getPassword(),
            customer.getPhoneNumber(), customer.getAddress()
        );
    }

    public Optional<Customer> findByUsername(final String username) {
        final String sql = "SELECT id, username, password, phoneNumber, address FROM customer WHERE username = :username";
        final SqlParameterSource params = new MapSqlParameterSource("username", username);

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, params, customerMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private RowMapper<Customer> customerMapper() {
        return (resultSet, rowNum) -> Customer.of(
            resultSet.getLong("id"),
            resultSet.getString("username"),
            resultSet.getString("password"),
            resultSet.getString("phoneNumber"),
            resultSet.getString("address")
        );
    }

    public void update(final Customer customer) {
        final String sql = "UPDATE customer SET password = :password, phoneNumber = :phoneNumber, address = :address WHERE id = :id";
        final SqlParameterSource params = new MapSqlParameterSource("password", customer.getPassword().getValue())
            .addValue("phoneNumber", customer.getPhoneNumber().getValue())
            .addValue("address", customer.getAddress())
            .addValue("id", customer.getId());

        jdbcTemplate.update(sql, params);
    }

    public void deleteByUsername(final String username) {
        final String sql = "DELETE FROM customer WHERE username = :username";
        final SqlParameterSource params = new MapSqlParameterSource("username", username);

        jdbcTemplate.update(sql, params);
    }
}

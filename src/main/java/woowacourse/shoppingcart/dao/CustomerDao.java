package woowacourse.shoppingcart.dao;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Repository
public class CustomerDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertActor;

    public CustomerDao(DataSource dataSource, NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertActor = new SimpleJdbcInsert(dataSource)
            .withTableName("customer")
            .usingGeneratedKeyColumns("id");
    }

    public Long findIdByUserName(String userName) {
        try {
            final String query = "SELECT id, email, username, password FROM customer WHERE username = :username";
            Map<String, String> params = Map.of("username", userName.toLowerCase(Locale.ENGLISH));
            Customer customer = jdbcTemplate.queryForObject(query, params, rowMapper());

            return customer.getId();
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public Optional<Customer> findById(long id) {
        try {
            final String query = "SELECT id, email, username, password FROM customer WHERE id = :id";
            Map<String, Long> params = Map.of("id", id);
            Customer customer = jdbcTemplate.queryForObject(query, params, rowMapper());

            return Optional.ofNullable(customer);
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Customer> findByEmail(String email) {
        try {
            final String query = "SELECT id, email, username, password FROM customer WHERE email = :email";
            Map<String, String> params = Map.of("email", email);
            Customer customer = jdbcTemplate.queryForObject(query, params, rowMapper());

            return Optional.ofNullable(customer);
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Customer> findByUsername(String username) {
        try {
            final String query = "SELECT id, email, username, password FROM customer WHERE username = :username";
            Map<String, String> params = Map.of("username", username);
            Customer customer = jdbcTemplate.queryForObject(query, params, rowMapper());

            return Optional.ofNullable(customer);
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Customer> findByEmailAndPassword(String email, String password) {
        try {
            final String query = "SELECT id, email, username, password FROM customer WHERE email = :email and password = :password";
            Map<String, String> params = Map.of("email", email, "password", password);
            Customer customer = jdbcTemplate.queryForObject(query, params, rowMapper());

            return Optional.ofNullable(customer);
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Long save(Customer customer) {
        return insertActor.executeAndReturnKey(new MapSqlParameterSource()
            .addValue("email", customer.getEmail())
            .addValue("username", customer.getUsername())
            .addValue("password", customer.getPassword())
        ).longValue();
    }

    public void update(long id, String username) {
        String sql = "update customer set username = :username "
            + "where id = :id";
        jdbcTemplate.update(sql, new MapSqlParameterSource()
            .addValue("id", id)
            .addValue("username", username)
        );
    }

    private RowMapper<Customer> rowMapper() {
        return ((rs, rowNum) -> new Customer(
            rs.getLong("id"),
            rs.getString("email"),
            rs.getString("username"),
            rs.getString("password")
        ));
    }

    public void delete(long id) {
        String sql = "delete from customer where id = :id";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource("id", id);

        jdbcTemplate.update(sql, namedParameters);
    }
}

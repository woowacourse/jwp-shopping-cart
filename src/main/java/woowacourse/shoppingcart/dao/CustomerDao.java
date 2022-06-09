package woowacourse.shoppingcart.dao;

import java.util.Objects;
import java.util.Optional;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.customdbaccess.ExtendedSqlParameterSource;
import woowacourse.shoppingcart.domain.Age;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Nickname;
import woowacourse.shoppingcart.domain.Password;
import woowacourse.shoppingcart.domain.Username;

@Repository
public class CustomerDao {

    private static final RowMapper<Customer> ROW_MAPPER = (resultSet, rowNum) ->
            Customer.of(
                    resultSet.getLong("id"),
                    new Username(resultSet.getString("username")),
                    new Password(resultSet.getString("password")),
                    new Nickname(resultSet.getString("nickname")),
                    new Age(resultSet.getInt("age")));

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CustomerDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Customer> findById(Long id) {
        final String sql = "SELECT id, username, password, nickname, age FROM customer "
                + "WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        return jdbcTemplate.query(sql, params, ROW_MAPPER)
                .stream().findAny();
    }

    public Optional<Customer> findByUserName(String userName) {
        final String sql = "SELECT id, username, password, nickname, age FROM customer "
                + "WHERE username = :username";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", userName);

        return jdbcTemplate.query(sql, params, ROW_MAPPER)
                .stream().findAny();
    }

    public Long findIdByUserName(String userName) {
        final String sql = "SELECT id FROM customer WHERE username = :username";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", userName);

        return jdbcTemplate.queryForObject(sql, params, Long.class);
    }

    public Long save(Customer customer) {
        final String sql = "INSERT INTO customer(username, password, nickname, age) "
                + "VALUES(:username, :password, :nickname, :age)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource params = new ExtendedSqlParameterSource(customer);

        jdbcTemplate.update(sql, params, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public void update(Customer customer) {
        final String sql = "UPDATE customer SET username = :username, "
                + "password = :password, nickname = :nickname, age = :age "
                + "WHERE id = :id";
        SqlParameterSource params = new ExtendedSqlParameterSource(customer);

        jdbcTemplate.update(sql, params);
    }

    public void delete(Customer customer) {
        final String sql = "DELETE FROM customer WHERE id = :id";
        SqlParameterSource params = new MapSqlParameterSource("id", customer.getId());

        jdbcTemplate.update(sql, params);
    }

    public boolean exists(Customer customer) {
        String sql = "select exists (select 1 from customer where username = :username)";
        SqlParameterSource params = new MapSqlParameterSource("username", customer.getUsername().getValue());
        return Objects.requireNonNull(jdbcTemplate.queryForObject(sql, params, Boolean.class));
    }
}

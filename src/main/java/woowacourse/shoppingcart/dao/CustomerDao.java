package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Repository
public class CustomerDao {

    private static final RowMapper<Customer> CUSTOMER_ROW_MAPPER = (rs, rowNum) -> new Customer(
        rs.getLong("id"),
        rs.getString("email"),
        rs.getString("nickname"),
        rs.getString("password")
    );

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CustomerDao(final JdbcTemplate jdbcTemplate, final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Long findIdByNickname(final String nickname) {
        try {
            String query = "SELECT id FROM customer WHERE nickname = ?";
            return jdbcTemplate.queryForObject(query, Long.class, nickname);
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public Long save(Customer customer) {
        String query = "INSERT INTO customer (email, nickname, password) VALUES (:email, :nickname, :password)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameters = new MapSqlParameterSource()
          .addValue("email", customer.getEmail())
          .addValue("nickname", customer.getNickname())
          .addValue("password", customer.getPassword());

        namedParameterJdbcTemplate.update(query, parameters, keyHolder,  new String[]{"id"});
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public boolean existsByNickname(String nickname) {
        String query = "SELECT EXISTS (SELECT * FROM customer WHERE nickname = ?)";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(query, Boolean.class, nickname));
    }

    public Optional<Customer> findIdByEmail(final String email) {
        try {
            String query = "SELECT id, email, nickname, password FROM customer WHERE email = ?";
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, CUSTOMER_ROW_MAPPER, email));
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void deleteByEmail(String email) {
        String query = "DELETE FROM customer WHERE email = ?";
        jdbcTemplate.update(query, email);
    }

    public void updatePassword(Customer customer) {
        String query = "UPDATE customer SET password = ? WHERE id = ?";
        jdbcTemplate.update(query, customer.getPassword(), customer.getId());
    }

    public void updateNickname(Customer customer) {
        String query = "UPDATE customer SET nickname = ? WHERE id = ?";
        jdbcTemplate.update(query, customer.getNickname(), customer.getId());
    }

    public boolean existsByEmail(String email) {
        String query = "SELECT EXISTS (SELECT * FROM customer WHERE email = ?)";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(query, Boolean.class, email));
    }
}

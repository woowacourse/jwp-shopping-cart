package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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

    public CustomerDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long findIdByNickname(final String nickname) {
        try {
            final String query = "SELECT id FROM customer WHERE nickname = ?";
            return jdbcTemplate.queryForObject(query, Long.class,
                nickname.toLowerCase(Locale.ROOT));
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public Long save(Customer customer) {
        String query = "INSERT INTO customer (email, nickname, password) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(query,
                new String[]{"id"});
            preparedStatement.setString(1, customer.getEmail());
            preparedStatement.setString(2, customer.getNickname());
            preparedStatement.setString(3, customer.getPassword());
            return preparedStatement;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public boolean existsByNickname(String nickname) {
        String query = "SELECT EXISTS (SELECT * FROM customer WHERE nickname = ?)";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(query, Boolean.class, nickname));
    }

    public Optional<Customer> findIdByEmail(final String email) {
        try {
            final String query = "SELECT id, email, nickname, password FROM customer WHERE email = ?";
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, CUSTOMER_ROW_MAPPER, email));
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}

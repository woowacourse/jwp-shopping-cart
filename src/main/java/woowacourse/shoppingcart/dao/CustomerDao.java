package woowacourse.shoppingcart.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

import java.sql.PreparedStatement;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@Repository
public class CustomerDao {

    private static final RowMapper<Customer> CUSTOMER_ROW_MAPPER = (resultSet, rowNum) -> {
        return Customer.from(
                resultSet.getLong("id"),
                resultSet.getString("user_id"),
                resultSet.getString("nickname"),
                resultSet.getString("password")
        );
    };

    private static final boolean NOT_WITHDRAWAL = false;
    private static final boolean WITHDRAWAL = true;

    private final JdbcTemplate jdbcTemplate;

    public CustomerDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long findIdByUserName(final String userName) {
        try {
            final String query = "SELECT id FROM customer WHERE username = ?";
            return jdbcTemplate.queryForObject(query, Long.class, userName.toLowerCase(Locale.ROOT));
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public Long save(final Customer customer) {
        String query = "INSERT INTO customer (user_id, nickname, password, withdrawal) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(query, new String[]{"id"});
            preparedStatement.setString(1, customer.getUserId());
            preparedStatement.setString(2, customer.getNickname());
            preparedStatement.setString(3, customer.getPassword());
            preparedStatement.setBoolean(4, NOT_WITHDRAWAL);
            return preparedStatement;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public Optional<Customer> findById(final Long id) {
        String query = "SELECT id, user_id, nickname, password FROM customer WHERE id = ? and withdrawal = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, CUSTOMER_ROW_MAPPER, id, NOT_WITHDRAWAL));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    public Optional<Customer> findByUserId(final String userId) {
        String query = "SELECT id, user_id, nickname, password FROM customer WHERE user_id = ? and withdrawal = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, CUSTOMER_ROW_MAPPER, userId, NOT_WITHDRAWAL));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    public Optional<Customer> findByNickname(final String nickname) {
        String query = "SELECT id, user_id, nickname, password FROM customer WHERE nickname = ? and withdrawal = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, CUSTOMER_ROW_MAPPER, nickname, NOT_WITHDRAWAL));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    public void updateNickname(final Long id, final String nickname) {
        String query = "UPDATE customer SET nickname = ? WHERE id = ? and withdrawal = ?";
        jdbcTemplate.update(query, nickname, id, NOT_WITHDRAWAL);
    }

    public void updatePassword(final Long id, final String password) {
        String query = "UPDATE customer SET password = ? WHERE id = ? and withdrawal = ?";
        jdbcTemplate.update(query, password, id, NOT_WITHDRAWAL);
    }

    public void delete(final Long id) {
        String query = "UPDATE customer SET withdrawal = ? WHERE id = ? and withdrawal = ?";
        jdbcTemplate.update(query, WITHDRAWAL, id, NOT_WITHDRAWAL);
    }
}

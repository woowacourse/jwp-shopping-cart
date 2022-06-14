package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.Locale;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.exception.DuplicateCustomerException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Repository
public class CustomerDao {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Customer> memberRowMapper = (rs, rn) -> new Customer(
            rs.getLong("id"),
            rs.getString("email"),
            rs.getString("password"),
            rs.getString("username")
    );

    public CustomerDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(final Customer customer) {
        final String query = "INSERT INTO customer(email, password, username) values(?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                final PreparedStatement preparedStatement = connection.prepareStatement(query, new String[]{"id"});
                preparedStatement.setString(1, customer.getEmail());
                preparedStatement.setString(2, customer.getPassword());
                preparedStatement.setString(3, customer.getUsername());
                return preparedStatement;
            }, keyHolder);

            return keyHolder.getKey().longValue();
        } catch (final DataIntegrityViolationException e) {
            throw new DuplicateCustomerException("이미 가입된 닉네임입니다.");
        }
    }

    public Optional<Customer> findById(final Long createdMemberId) {
        final String query = "SELECT id, email, password, username FROM customer WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, memberRowMapper, createdMemberId));
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Customer> findByEmail(final String email) {
        final String query = "SELECT id, email, password, username FROM customer WHERE email = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, memberRowMapper, email));
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Long findByUsername(final String username) {
        final String query = "SELECT id FROM customer WHERE username = ?";
        try {
            return jdbcTemplate.queryForObject(query, Long.class, username.toLowerCase(Locale.ROOT));
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public void update(final Customer customer) {
        final String query = "UPDATE customer SET username = ? WHERE id = ?";

        final int changedRowCount = jdbcTemplate.update(connection -> {
            final PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, customer.getUsername());
            preparedStatement.setLong(2, customer.getId());
            return preparedStatement;
        });

        checkAffectedRowCount(changedRowCount);
    }

    public void deleteById(final Long id) {
        final String query = "DELETE FROM customer WHERE id = ?";

        final int deletedRowCount = jdbcTemplate.update(connection -> {
            final PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            return preparedStatement;
        });

        checkAffectedRowCount(deletedRowCount);
    }

    private void checkAffectedRowCount(final int affectedRowCount) {
        if (affectedRowCount != 1) {
            throw new InvalidCustomerException();
        }
    }
}


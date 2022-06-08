package woowacourse.shoppingcart.dao;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.Email;
import woowacourse.shoppingcart.domain.customer.Username;
import woowacourse.shoppingcart.exception.DuplicateNameException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

import java.sql.PreparedStatement;
import java.util.Locale;
import java.util.Optional;

@Repository
public class CustomerDao {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Customer> memberRowMapper = (rs, rn) -> new Customer(
            rs.getLong("id"),
            new Email(rs.getString("email")),
            rs.getString("password"),
            new Username(rs.getString("username"))
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
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateNameException("이미 가입된 닉네임입니다.");
        }
        return keyHolder.getKey().longValue();
    }

    public Optional<Customer> findById(final Long createdMemberId) {
        final String query = "SELECT id, email, password, username FROM customer WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, memberRowMapper, createdMemberId));
        } catch (EmptyResultDataAccessException e) {
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
        try {
            final int changedRowCount = jdbcTemplate.update(connection -> {
                final PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, customer.getUsername());
                preparedStatement.setLong(2, customer.getId());
                return preparedStatement;
            });
            checkAffectedRowCount(changedRowCount);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateNameException("이미 존재하는 닉네임입니다.");
        }
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

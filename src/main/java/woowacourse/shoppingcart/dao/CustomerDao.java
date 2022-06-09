package woowacourse.shoppingcart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Repository
public class CustomerDao {

    private static final RowMapper<Customer> CUSTOMER_ROW_MAPPER = (resultSet, rowNum) ->
            new Customer(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("password"),
                    resultSet.getString("email"),
                    resultSet.getString("address"),
                    resultSet.getString("phone_number")
            );
    private final JdbcTemplate jdbcTemplate;

    public CustomerDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long findIdByName(final String name) {
        try {
            final String query = "SELECT id FROM customer WHERE name = ?";
            return jdbcTemplate.queryForObject(query, Long.class, name.toLowerCase(Locale.ROOT));
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public Optional<Long> save(final Customer customer) {
        final String query = "INSERT INTO customer (name, password, email, address, phone_number) "
                + "VALUES (?, ?, ?, ?, ?)";
        KeyHolder holder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update((Connection con) -> {
                PreparedStatement statement = con.prepareStatement(query, new String[]{"id"});
                statement.setString(1, customer.getName());
                statement.setString(2, customer.getPassword());
                statement.setString(3, customer.getEmail());
                statement.setString(4, customer.getAddress());
                statement.setString(5, customer.getPhoneNumber());
                return statement;
            }, holder);
            return Optional.of(Objects.requireNonNull(holder.getKey()).longValue());
        } catch (DuplicateKeyException | NullPointerException e) {
            return Optional.empty();
        }
    }

    public Optional<Customer> findByName(final String name) {
        final String query = "SELECT * FROM customer WHERE name = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, CUSTOMER_ROW_MAPPER, name));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Customer> findById(final Long id) {
        final String query = "SELECT * FROM customer WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, CUSTOMER_ROW_MAPPER, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public boolean update(final Customer customer) {
        final String query = "UPDATE customer SET address = ?, phone_number = ? WHERE id = ?";
        return isUpdated(
                jdbcTemplate.update(query, customer.getAddress(), customer.getPhoneNumber(), customer.getId()));
    }

    private boolean isUpdated(final int updatedCount) {
        return updatedCount > 0;
    }

    public boolean deleteByName(final String name) {
        final String query = "DELETE FROM customer WHERE name = ?";
        return isUpdated(jdbcTemplate.update(query, name));
    }
}

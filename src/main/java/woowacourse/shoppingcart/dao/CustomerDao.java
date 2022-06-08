package woowacourse.shoppingcart.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.customer.Customer;

import java.sql.PreparedStatement;
import java.util.Objects;
import java.util.Optional;

@Repository
public class CustomerDao {

    private static final RowMapper<Customer> ROW_MAPPER = (rs, rowNum) -> {
        String email = rs.getString("email");
        String password = rs.getString("password");
        String nickname = rs.getString("nickname");
        Long id = rs.getLong("id");
        return new Customer(id, email, password, nickname);
    };
    private final JdbcTemplate jdbcTemplate;

    public CustomerDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Customer save(Customer customer) {
        final String query = "INSERT INTO customer (email, password, nickname) VALUES (?, ?, ?)";
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement preparedStatement =
                    connection.prepareStatement(query, new String[]{"id"});
            preparedStatement.setString(1, customer.getEmail());
            preparedStatement.setString(2, customer.getPassword());
            preparedStatement.setString(3, customer.getNickname());
            return preparedStatement;
        }, keyHolder);

        long customerId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return new Customer(customerId, customer.getEmail(), customer.getPassword(), customer.getNickname());
    }

    public Optional<Customer> findById(Long id) {
        try {
            final String query = "SELECT email, password, nickname, id FROM customer WHERE id = ?";
            Customer customer = jdbcTemplate.queryForObject(query, ROW_MAPPER, id);
            return Optional.of(customer);
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Customer> findByEmail(String email) {
        try {
            final String query = "SELECT email, password, nickname, id FROM customer WHERE email = ?";
            Customer customer = jdbcTemplate.queryForObject(query, ROW_MAPPER, email);
            return Optional.of(customer);
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public boolean existByEmail(String email) {
        final String query = "SELECT EXISTS(SELECT id FROM customer WHERE email = ?)";
        return jdbcTemplate.queryForObject(query, Boolean.class, email);
    }

    public void deleteByEmail(String email) {
        final String query = "DELETE FROM customer WHERE email = ?";
        jdbcTemplate.update(query, email);
    }

    public void update(Customer customer) {
        final String query = "UPDATE customer SET password = ?, nickname = ? WHERE id = ?";
        jdbcTemplate.update(query, customer.getPassword(), customer.getNickname(), customer.getId());
    }
}

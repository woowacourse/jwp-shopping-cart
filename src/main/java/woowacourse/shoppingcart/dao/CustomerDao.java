package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.Objects;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.customer.Customer;

@Repository
public class CustomerDao {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Customer> rowMapper = (rs, rowNum) ->
            new Customer(
                    rs.getLong("id"),
                    rs.getString("nickname"),
                    rs.getString("email"),
                    rs.getString("password"));

    public CustomerDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(Customer customer) {
        final String query = "INSERT INTO customer (nickname, email, password) VALUES (?, ?, ?)";
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement preparedStatement =
                    connection.prepareStatement(query, new String[]{"id"});
            preparedStatement.setString(1, customer.getNickname());
            preparedStatement.setString(2, customer.getEmail());
            preparedStatement.setString(3, customer.getPassword());
            return preparedStatement;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public boolean existEmail(String email) {
        final String query = "SELECT EXISTS(SELECT * FROM customer WHERE email = ?)";
        return jdbcTemplate.queryForObject(query, Boolean.class, email);
    }

    public Customer findByEmail(String email) {
        try {
            final String query = "SELECT * FROM customer WHERE email = ?";
            return jdbcTemplate.queryForObject(query, rowMapper, email);
        } catch (EmptyResultDataAccessException exception) {
            throw new IllegalArgumentException("이메일이 존재하지 않습니다.");
        }
    }

    public void updateById(Long id, Customer updatedCustomer) {
        final String query = "UPDATE customer SET nickname = ?, password = ? WHERE id = ?";
        jdbcTemplate.update(query, updatedCustomer.getNickname(), updatedCustomer.getPassword(), id);
    }

    public void deleteById(Long id) {
        final String query = "DELETE FROM customer WHERE id = ?";
        jdbcTemplate.update(query, id);
    }
}

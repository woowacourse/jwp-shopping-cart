package woowacourse.shoppingcart.dao;

import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Repository
public class CustomerDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public CustomerDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("customer")
            .usingGeneratedKeyColumns("id");
    }

    public Long findIdByUserName(final String userName) {
        try {
            final String query = "SELECT id FROM customer WHERE username = ?";
            return jdbcTemplate.queryForObject(query, Long.class, userName);
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public Customer save(Customer customer) {
        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("username", customer.getUsername().getValue())
            .addValue("password", customer.getPassword().getValue())
            .addValue("phoneNumber", customer.getPhoneNumber().getValue())
            .addValue("address", customer.getAddress());
        Number newId = jdbcInsert.executeAndReturnKey(params);

        return new Customer(
            newId.longValue(),
            customer.getUsername(),
            customer.getPassword(),
            customer.getPhoneNumber(),
            customer.getAddress()
        );
    }

    public Optional<Customer> findByUsername(String username) {
        final String query = "SELECT id, username, password, phoneNumber, address FROM customer WHERE username = ?";

        try {
            return Optional.of(jdbcTemplate.queryForObject(query, customerMapper(), username));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    private RowMapper<Customer> customerMapper(){
        return (resultSet, rowNum) -> Customer.of(
            resultSet.getLong("id"),
            resultSet.getString("username"),
            resultSet.getString("password"),
            resultSet.getString("phoneNumber"),
            resultSet.getString("address")
        );
    }

    public void update(Customer customer) {
        final String query = "UPDATE customer SET password = ?, phoneNumber = ?, address = ? WHERE id = ?";
        jdbcTemplate.update(query,
            customer.getPassword().getValue(),
            customer.getPhoneNumber().getValue(),
            customer.getAddress(),
            customer.getId()
        );
    }

    public void deleteByUsername(String username) {
        final String query = "DELETE FROM customer WHERE username = ?";
        jdbcTemplate.update(query, username);
    }
}

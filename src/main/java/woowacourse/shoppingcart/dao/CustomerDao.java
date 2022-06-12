package woowacourse.shoppingcart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Optional;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.DistinctAttribute;
import woowacourse.shoppingcart.domain.customer.UserName;
import woowacourse.shoppingcart.exception.UnexpectedException;

@Repository
public class CustomerDao {

    public static final String COLUMN_USERNAME = "name";
    public static final String COLUMN_EMAIL = "email";

    private static final RowMapper<Customer> CUSTOMER_ROW_MAPPER = (resultSet, rowNum) ->
        Customer.fromSaved(
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

    public Optional<Long> save(final Customer customer) {
        final String query = "INSERT INTO customer (name, password, email, address, phone_number, is_deleted) "
            + "VALUES (?, ?, ?, ?, ?, 0)";
        KeyHolder holder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update((Connection con) -> {
                PreparedStatement statement = con.prepareStatement(query, new String[] {"id"});
                statement.setString(1, customer.getName());
                statement.setString(2, customer.getPassword());
                statement.setString(3, customer.getEmail());
                statement.setString(4, customer.getAddress());
                statement.setString(5, customer.getPhoneNumber());
                return statement;
            }, holder);
            return Optional.of(DaoSupporter.getGeneratedId(holder,
                () -> new UnexpectedException("회원 추가 중 알수없는 예외가 발생했습니다.")));
        } catch (DuplicateKeyException e) {
            return Optional.empty();
        }
    }

    public Optional<Customer> findByName(final UserName userName) {
        final String query = "SELECT * FROM customer WHERE name = ? and is_deleted = 0";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, CUSTOMER_ROW_MAPPER, userName.getValue()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Customer> findById(final Long id) {
        final String query = "SELECT * FROM customer WHERE id = ? and is_deleted = 0";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, CUSTOMER_ROW_MAPPER, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public boolean update(Customer customer) {
        final String query = "UPDATE customer SET address = ?, phone_number = ? WHERE id = ? and is_deleted = 0";
        return DaoSupporter.isUpdated(
            jdbcTemplate.update(query, customer.getAddress(), customer.getPhoneNumber(), customer.getId())
        );
    }

    public boolean deleteById(Long id) {
        final String query = "UPDATE customer SET is_deleted = 1 WHERE id = ?";
        return DaoSupporter.isUpdated(jdbcTemplate.update(query, id));
    }

    public boolean isDuplicated(String column, DistinctAttribute attribute) {
        final String query = String.format("SELECT EXISTS(SELECT 1 FROM customer WHERE %s = ? and is_deleted = 0)",
            column);
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(query, Boolean.class, attribute.getDistinctive()));
    }
}

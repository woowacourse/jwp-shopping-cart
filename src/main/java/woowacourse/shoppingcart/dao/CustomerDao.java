package woowacourse.shoppingcart.dao;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.exception.shoppingcart.InvalidCustomerException;
import woowacourse.shoppingcart.domain.Customer;

@Repository
public class CustomerDao {

    private static final RowMapper<Customer> ROW_MAPPER = (resultSet, rowNum) -> new Customer(
            resultSet.getLong("id"),
            resultSet.getString("login_id"),
            resultSet.getString("name"),
            resultSet.getString("password")
    );

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert simpleJdbc;

    public CustomerDao(final DataSource dataSource) {
        this.simpleJdbc = new SimpleJdbcInsert(dataSource)
                .withTableName("customer")
                .usingGeneratedKeyColumns("id");
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public Long save(final Customer customer) {
        try {
            SqlParameterSource parameters = new BeanPropertySqlParameterSource(customer);
            return simpleJdbc.executeAndReturnKey(parameters).longValue();
        } catch (DuplicateKeyException e) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
    }

    public Long findIdByUserName(final String userName) {
        try {
            final String query = "SELECT id FROM customer WHERE name = :username";
            return namedParameterJdbcTemplate.queryForObject(query,
                    Map.of("username", userName.toLowerCase(Locale.ROOT)), Long.class);
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public Customer findById(Long id) {
        try {
            final String query = "SELECT id, login_id, name, password FROM customer WHERE id = :id";
            return namedParameterJdbcTemplate.queryForObject(query, Map.of("id", id), ROW_MAPPER);
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public Customer findByLoginId(String loginId) {
        try {
            final String query = "SELECT id, login_id, name, password FROM customer WHERE login_id = :loginId";
            return namedParameterJdbcTemplate.queryForObject(query, Map.of("loginId", loginId), ROW_MAPPER);
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public void update(Customer customer) {
        try {
            final String query = "UPDATE customer SET name = :name, password = :password WHERE login_id = :loginId";
            Map<String, Object> params = new HashMap<>();
            params.put("loginId", customer.getLoginId());
            params.put("name", customer.getName());
            params.put("password", customer.getPassword());
            int affectedRows = namedParameterJdbcTemplate.update(query, params);
            checkAffectedRow(affectedRows);
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    private void checkAffectedRow(int rows) {
        if (rows == 0) {
            throw new InvalidCustomerException();
        }
    }

    public void delete(String loginId) {
        try {
            final String query = "DELETE FROM customer WHERE login_id = :loginId";
            namedParameterJdbcTemplate.update(query, Map.of("loginId", loginId));
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }
}

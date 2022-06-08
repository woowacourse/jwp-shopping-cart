package woowacourse.shoppingcart.dao;

import java.util.HashMap;
import java.util.Map;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.common.exception.LoginException;
import woowacourse.common.exception.NotFoundException;
import woowacourse.common.exception.dto.ErrorResponse;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.Password;

@Repository
public class CustomerDao {

    private static final RowMapper<Customer> CUSTOMER_ROW_MAPPER = ((rs, rowNum) ->
            new Customer(rs.getLong("id"),
                    rs.getString("email"),
                    Password.fromEncryptedInput(rs.getString("password")),
                    rs.getString("username")));

    private final JdbcTemplate jdbcTemplate;

    public CustomerDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long getIdByEmail(final String email) {
        try {
            final String query = "SELECT id FROM customer WHERE email = ?";
            return jdbcTemplate.queryForObject(query, Long.class, email);
        } catch (final EmptyResultDataAccessException e) {
            throw new NotFoundException("존재하지 않는 사용자입니다.", ErrorResponse.NOT_EXIST_CUSTOMER);
        }
    }

    public void save(Customer customer) {
        final SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("customer")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> params = new HashMap<>();
        params.put("email", customer.getEmail());
        params.put("username", customer.getUsername());
        params.put("password", customer.getPassword());

        simpleJdbcInsert.execute(params);
    }

    public Customer getByEmail(String email) {
        final String sql = "SELECT id, email, password, username FROM customer WHERE email = ?";
        try {
            return jdbcTemplate.queryForObject(sql, CUSTOMER_ROW_MAPPER, email);
        } catch (EmptyResultDataAccessException e) {
            throw new LoginException("존재하지 않는 이메일입니다.", ErrorResponse.LOGIN_FAIL);
        }
    }

    public void updatePassword(Long id, String password) {
        final String sql = "UPDATE customer SET password = ? WHERE id = ?";
        jdbcTemplate.update(sql, password, id);
    }

    public void updateGeneralInfo(Long id, String username) {
        final String sql = "UPDATE customer SET username = ? WHERE id = ?";
        jdbcTemplate.update(sql, username, id);
    }

    public void delete(Long id) {
        final String sql = "DELETE FROM customer WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public boolean existsByEmail(String email) {
        final String sql = "SELECT exists(SELECT id FROM customer WHERE email = ?)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, email);
    }
}

package woowacourse.shoppingcart.customer.dao;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.exception.LoginException;
import woowacourse.exception.dto.ErrorResponse;
import woowacourse.shoppingcart.customer.domain.Customer;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Repository
public class CustomerDao {

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

    public Customer findByEmail(String email) {
        final String sql = "SELECT id, email, password, username FROM customer WHERE email = ?";
        try {
            return jdbcTemplate.queryForObject(sql, ((rs, rowNum) -> new Customer(rs.getLong("id"
            ), rs.getString("email"), rs.getString("password"), rs.getString("username"))), email);
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
        final String sql = "SELECT exists(SELECT * FROM customer WHERE email = ?)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, email);
    }
}

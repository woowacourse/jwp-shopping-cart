package woowacourse.shoppingcart.dao;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.exception.LoginException;
import woowacourse.exception.dto.ErrorResponse;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.Email;
import woowacourse.shoppingcart.domain.customer.Password;
import woowacourse.shoppingcart.domain.customer.Username;
import woowacourse.shoppingcart.exception.NotExistException;

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
            throw new NotExistException("고객을 찾을 수 없습니다.", ErrorResponse.NOT_EXIST_CUSTOMER);
        }
    }

    public Long save(Customer customer) {
        final SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("customer")
                .usingGeneratedKeyColumns("id");
        Map<String, Object> params = new HashMap<>();
        params.put("email", customer.getEmail().getValue());
        params.put("username", customer.getUsername().getValue());
        params.put("password", customer.getPassword().getValue());

        return (Long) simpleJdbcInsert.executeAndReturnKey(params);
    }

    public Customer findByEmail(String email) {
        final String sql = "SELECT id, email, password, username FROM customer WHERE email = ?";
        try {
            return jdbcTemplate.queryForObject(sql, ((rs, rowNum) ->
                    new Customer(rs.getLong("id"),
                            Email.of(rs.getString("email")),
                            Password.ofWithoutEncryption(rs.getString("password")),
                            Username.of(rs.getString("username")))), email);
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

    public boolean isExist(String email) {
        final String sql = "SELECT exists(SELECT * FROM customer WHERE email = ?)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, email);
    }
}

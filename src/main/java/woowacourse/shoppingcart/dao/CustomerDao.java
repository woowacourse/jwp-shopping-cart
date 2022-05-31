package woowacourse.shoppingcart.dao;

import java.util.HashMap;
import java.util.Map;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.dao.entity.CustomerEntity;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Repository
public class CustomerDao {

    private static final RowMapper<CustomerEntity> CUSTOMER_ENTITY_ROW_MAPPER = (rs, rowNum) -> new CustomerEntity(
            rs.getLong("id"),
            rs.getString("email"),
            rs.getString("nickname"),
            rs.getString("password"));

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CustomerDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long findIdByEmail(final String email) {
        final String query = "SELECT id FROM customer WHERE email = :email";

        Map<String, Object> params = new HashMap<>();
        params.put("email", email);

        try {
            return jdbcTemplate.queryForObject(query, params, Long.class);
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public CustomerEntity findByEmailAndPassword(final String email, final String password) {
        final String sql = "SELECT id, email, nickname, password FROM customer "
                + "WHERE email = :email and password = :password";

        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);

        try {
            return jdbcTemplate.queryForObject(sql, params, CUSTOMER_ENTITY_ROW_MAPPER);
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException("아이디나 비밀번호를 잘못 입력했습니다.");
        }
    }

    public boolean existEmail(String email) {
        final String sql = "select exists (select * from customer where email = :email)";

        Map<String, Object> params = new HashMap<>();
        params.put("email", email);

        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, params, Boolean.class));
    }
}

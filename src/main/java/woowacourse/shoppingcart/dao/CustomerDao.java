package woowacourse.shoppingcart.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.dao.entity.CustomerEntity;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Email;
import woowacourse.shoppingcart.domain.Nickname;
import woowacourse.shoppingcart.domain.Password;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Repository
public class CustomerDao {

    private static final RowMapper<CustomerEntity> CUSTOMER_ENTITY_ROW_MAPPER = (rs, rowNum) -> new CustomerEntity(
            rs.getLong("id"),
            rs.getString("email"),
            rs.getString("nickname"),
            rs.getString("password"));

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public CustomerDao(final NamedParameterJdbcTemplate jdbcTemplate, final DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("customer")
                .usingGeneratedKeyColumns("id");
    }

    public Long findIdByEmail(final Email email) {
        final String query = "SELECT id FROM customer WHERE email = :email";

        final Map<String, Object> params = new HashMap<>();
        params.put("email", email.getValue());

        try {
            return jdbcTemplate.queryForObject(query, params, Long.class);
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public CustomerEntity findByEmail(final Email email) {
        final String sql = "SELECT id, email, nickname, password FROM customer "
                + "WHERE email = :email";

        final Map<String, Object> params = new HashMap<>();
        params.put("email", email.getValue());

        try {
            return jdbcTemplate.queryForObject(sql, params, CUSTOMER_ENTITY_ROW_MAPPER);
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public Optional<CustomerEntity> findByEmailAndPassword(final Email email, final Password password) {
        final String sql = "SELECT id, email, nickname, password FROM customer "
                + "WHERE email = :email and password = :password";

        final Map<String, Object> params = new HashMap<>();
        params.put("email", email.getValue());
        params.put("password", password.getValue());

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, params, CUSTOMER_ENTITY_ROW_MAPPER));
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public boolean existEmail(final Email email) {
        final String sql = "select exists (select * from customer where email = :email)";

        final Map<String, Object> params = new HashMap<>();
        params.put("email", email.getValue());

        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, params, Boolean.class));
    }


    public Long save(final Customer customer) {
        final Map<String, Object> params = new HashMap<>();
        params.put("email", customer.getEmail());
        params.put("nickname", customer.getNickname());
        params.put("password", customer.getPassword());

        try {
            return simpleJdbcInsert.executeAndReturnKey(params).longValue();
        } catch (DuplicateKeyException e) {
            throw new InvalidCustomerException("이미 존재하는 이메일입니다.");
        }
    }

    public void updateInfo(final Email email, final Nickname nickname) {
        final String sql = "update customer set nickname = :nickname where email = :email";

        final Map<String, Object> params = new HashMap<>();
        params.put("email", email.getValue());
        params.put("nickname", nickname.getValue());

        if (jdbcTemplate.update(sql, params) == 0) {
            throw new InvalidCustomerException();
        }
    }

    public void updatePassword(final Email email, final Password password) {
        final String sql = "update customer set password = :password where email = :email";

        final Map<String, Object> params = new HashMap<>();
        params.put("email", email.getValue());
        params.put("password", password.getValue());

        if (jdbcTemplate.update(sql, params) == 0) {
            throw new InvalidCustomerException();
        }
    }

    public void delete(final Email email) {
        final String sql = "delete from customer where email = :email";

        final Map<String, Object> params = new HashMap<>();
        params.put("email", email.getValue());

        if (jdbcTemplate.update(sql, params) == 0) {
            throw new InvalidCustomerException();
        }
    }
}

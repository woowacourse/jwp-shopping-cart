package woowacourse.shoppingcart.repository.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Repository
public class CustomerDao {

    private static final String REAL_CUSTOMER_QUERY = " (select id, username, password, nickname from customer where withdrawal = false) ";

    private static final RowMapper<Customer> ROW_MAPPER = (resultSet, rowNum) -> new Customer(
            resultSet.getLong("id"),
            resultSet.getString("username"),
            resultSet.getString("password"),
            resultSet.getString("nickname")
    );

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CustomerDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Long create(final Customer customer) {
        String query = "insert into customer (username, password, nickname, withdrawal)"
                + " values (:username, :password, :nickname, false)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource source = new BeanPropertySqlParameterSource(customer);
        namedParameterJdbcTemplate.update(query, source, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public Optional<Customer> findByUserName(final String username) {
        String query = "select id, username, password, nickname from"
                + REAL_CUSTOMER_QUERY
                + "where username = :username";
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(query, params, ROW_MAPPER));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Customer> findWithdrawalById(final Long id) {
        String query = "select id, username, password, nickname from customer where id = :id AND withdrawal = true";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(query, params, ROW_MAPPER));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Customer> findById(final Long id) {
        String query = "select id, username, password, nickname from"
                + REAL_CUSTOMER_QUERY
                + "where id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(query, params, ROW_MAPPER));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Customer> findByNickname(final String nickname) {
        String query = "select id, username, password, nickname from"
                + REAL_CUSTOMER_QUERY
                + "where nickname = :nickname";
        Map<String, Object> params = new HashMap<>();
        params.put("nickname", nickname);
        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(query, params, ROW_MAPPER));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Customer> findByUsernameAndPassword(final String username, final String password) {
        String query = "select id, username, password, nickname from"
                + REAL_CUSTOMER_QUERY
                + "where username = :username and password = :password";
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(query, params, ROW_MAPPER));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public int update(final Customer newCustomer) {
        String query = "update customer set nickname = :nickname where id = :id and exists" + REAL_CUSTOMER_QUERY;
        Map<String, Object> params = new HashMap<>();
        params.put("id", newCustomer.getId());
        params.put("nickname", newCustomer.getNickname());
        return namedParameterJdbcTemplate.update(query, params);
    }

    public int updatePassword(final Long id, final String oldPassword, final String newPassword) {
        String query = "update customer set password = :newPassword"
                + " where id = :id and password = :oldPassword and exists" + REAL_CUSTOMER_QUERY;
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("newPassword", newPassword);
        params.put("oldPassword", oldPassword);
        return namedParameterJdbcTemplate.update(query, params);
    }

    public int delete(final Long id) {
        String query = "update customer set withdrawal = true where id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return namedParameterJdbcTemplate.update(query, params);
    }
}

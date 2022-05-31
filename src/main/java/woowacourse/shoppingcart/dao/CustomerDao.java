package woowacourse.shoppingcart.dao;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.entity.CustomerEntity;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Repository
public class CustomerDao {

    private static final RowMapper<CustomerEntity> ROW_MAPPER = (rs, rowNum) -> new CustomerEntity(
            rs.getLong("id"),
            rs.getString("account"),
            rs.getString("nickname"),
            rs.getString("password"),
            rs.getString("address"),
            rs.getString("phone_number")
    );

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CustomerDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long findIdByUserName(final String userName) {
        try {
            final String sql = "SELECT id FROM customer WHERE account = :username";
            SqlParameterSource source = new MapSqlParameterSource("username",
                    userName.toLowerCase(Locale.ROOT));
            return jdbcTemplate.queryForObject(sql, source, Long.class);
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public void save(CustomerEntity customerEntity) {
        String sql = "INSERT INTO customer (account, nickname, password, address, phone_number) "
                + "VALUES (:account, :nickname, :password, :address, :phoneNumber)";
        SqlParameterSource source = new BeanPropertySqlParameterSource(customerEntity);
        jdbcTemplate.update(sql, source);
    }

    public boolean existsByAccount(String account) {
        String sql = "SELECT EXISTS (SELECT 1 FROM customer WHERE account = :account)";
        SqlParameterSource source = new MapSqlParameterSource("account", account);
        return Objects.requireNonNull(jdbcTemplate.queryForObject(sql, source, Boolean.class));
    }

    public CustomerEntity findByAccount(String account) {
        String sql = "SELECT * FROM customer WHERE account = :account";
        SqlParameterSource source = new MapSqlParameterSource("account", account);
        return jdbcTemplate.queryForObject(sql, source, ROW_MAPPER);
    }

    public Optional<CustomerEntity> findById(Long customerId) {
        String sql = "SELECT * FROM customer WHERE id = :customerId";
        SqlParameterSource source = new MapSqlParameterSource("customerId", customerId);
        return Optional.ofNullable(DataAccessUtils.singleResult(jdbcTemplate.query(sql, source, ROW_MAPPER)));
    }
}

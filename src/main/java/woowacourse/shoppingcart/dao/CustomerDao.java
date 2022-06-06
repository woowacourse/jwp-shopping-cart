package woowacourse.shoppingcart.dao;

import java.util.Objects;
import java.util.Optional;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.dao.entity.CustomerEntity;

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

    public Long save(CustomerEntity customerEntity) {
        String sql = "INSERT INTO customer (account, nickname, password, address, phone_number) "
                + "VALUES (:account, :nickname, :password, :address, :phoneNumber)";
        SqlParameterSource source = new BeanPropertySqlParameterSource(customerEntity);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, source, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public Optional<CustomerEntity> findById(Long customerId) {
        String sql = "SELECT id, account, nickname, password, address, phone_number FROM customer WHERE id = :customerId";
        SqlParameterSource source = new MapSqlParameterSource("customerId", customerId);
        return Optional.ofNullable(DataAccessUtils.singleResult(jdbcTemplate.query(sql, source, ROW_MAPPER)));
    }

    public Optional<CustomerEntity> findByAccount(String account) {
        String sql = "SELECT id, account, nickname, password, address, phone_number FROM customer WHERE account = :account";
        SqlParameterSource source = new MapSqlParameterSource("account", account);
        return Optional.ofNullable(DataAccessUtils.singleResult(jdbcTemplate.query(sql, source, ROW_MAPPER)));
    }

    public boolean existsById(Long customerId) {
        String sql = "SELECT EXISTS (SELECT 1 FROM customer WHERE id = :customerId)";
        SqlParameterSource source = new MapSqlParameterSource("customerId", customerId);
        return Objects.requireNonNull(jdbcTemplate.queryForObject(sql, source, Boolean.class));
    }

    public boolean existsByAccount(String account) {
        String sql = "SELECT EXISTS (SELECT 1 FROM customer WHERE account = :account)";
        SqlParameterSource source = new MapSqlParameterSource("account", account);
        return Objects.requireNonNull(jdbcTemplate.queryForObject(sql, source, Boolean.class));
    }

    public void update(CustomerEntity customerEntity) {
        String sql = "UPDATE customer SET nickname = :nickname, address = :address, phone_number = :phoneNumber "
                + "where id = :id";
        SqlParameterSource source = new BeanPropertySqlParameterSource(customerEntity);
        jdbcTemplate.update(sql, source);
    }

    public void deleteById(Long customerId) {
        String sql = "DELETE FROM customer WHERE id  = :customerId";
        SqlParameterSource source = new MapSqlParameterSource("customerId", customerId);
        jdbcTemplate.update(sql, source);
    }
}

package woowacourse.shoppingcart.dao;

import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.dao.entity.OrdersEntity;

@Repository
public class OrdersDao {

    private static final RowMapper<OrdersEntity> ROW_MAPPER = (rs, rownum) -> new OrdersEntity(
            rs.getLong("id"),
            rs.getLong("customer_id")
    );

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public OrdersDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(final Long customerId) {
        final String sql = "INSERT INTO orders (customer_id) VALUES (:id)";
        SqlParameterSource source = new MapSqlParameterSource("id", customerId);
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sql, source, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public List<OrdersEntity> findByCustomerId(final Long customerId) {
        final String sql = "SELECT id, customer_id FROM orders WHERE customer_id = :id";
        SqlParameterSource source = new MapSqlParameterSource("id", customerId);
        return jdbcTemplate.query(sql, source, ROW_MAPPER);
    }

    public boolean existsOrderId(final Long customerId, final Long orderId) {
        final String query = "SELECT EXISTS(SELECT * FROM orders WHERE customer_id = :customerId AND id = :id)";
        SqlParameterSource source = new MapSqlParameterSource()
                .addValue("id", orderId)
                .addValue("customerId", customerId);

        return Objects.requireNonNull(jdbcTemplate.queryForObject(query, source, Boolean.class));
    }
}

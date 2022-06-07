package woowacourse.shoppingcart.repository.dao;

import java.util.Map;
import java.util.Objects;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class OrdersDetailDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public OrdersDetailDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long create(final Long ordersId, final Long productId, final int quantity) {
        final String query = "INSERT INTO orders_detail (orders_id, product_id, quantity) VALUES (:ordersId, :productId, :quantity)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource source = new MapSqlParameterSource(
                Map.of("ordersId", ordersId, "productId", productId, "quantity", quantity)
        );
        jdbcTemplate.update(query, source, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }
}

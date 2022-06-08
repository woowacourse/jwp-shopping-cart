package woowacourse.shoppingcart.dao;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.entity.OrderEntity;

@Repository
public class OrderDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public OrderDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public OrderEntity save(OrderEntity order) {
        final String sql = "INSERT INTO orders(customer_id) VALUES(:customerId)";
        SqlParameterSource params = new BeanPropertySqlParameterSource(order);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sql, params, keyHolder);
        Number generatedId = keyHolder.getKey();
        return new OrderEntity(generatedId.longValue(), order.getCustomerId());
    }
}

package woowacourse.shoppingcart.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import woowacourse.shoppingcart.dao.dto.IdDto;
import woowacourse.shoppingcart.dao.dto.OrdersDto;

@Repository
public class OrderDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public OrderDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("orders")
                .usingGeneratedKeyColumns("id");
    }

    public Long addOrders(Long customerId) {
        OrdersDto ordersDto = new OrdersDto(customerId);
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(ordersDto);
        return simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();
    }

    public List<Long> findOrderIdsByCustomerId(Long customerId) {
        String sql = "SELECT id FROM orders WHERE customer_id = :id";
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(new IdDto(customerId));
        return jdbcTemplate.query(sql, parameterSource, (rs, rowNum) -> rs.getLong("id"));
    }

    public boolean isValidOrderId(Long customerId, Long orderId) {
        String sql = "SELECT EXISTS(SELECT * FROM orders WHERE customer_id = :customerId AND id = :id)";
        SqlParameterSource parameterSource = new MapSqlParameterSource("customerId", customerId)
                .addValue("id", orderId);
        return jdbcTemplate.queryForObject(sql, parameterSource, Boolean.class);
    }
}

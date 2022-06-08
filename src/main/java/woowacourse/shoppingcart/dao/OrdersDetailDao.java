package woowacourse.shoppingcart.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import woowacourse.shoppingcart.dao.dto.IdDto;
import woowacourse.shoppingcart.dao.dto.OrdersDetailDto;
import woowacourse.shoppingcart.domain.OrderDetail;

@Repository
public class OrdersDetailDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public OrdersDetailDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("orders_detail")
                .usingGeneratedKeyColumns("id");
    }

    public Long addOrdersDetail(Long ordersId, Long productId, int quantity) {
        OrdersDetailDto ordersDetailDto = new OrdersDetailDto(ordersId, productId, quantity);
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(ordersDetailDto);
        return simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();
    }

    public List<OrderDetail> findOrdersDetailsByOrderId(Long orderId) {
        String sql = "SELECT product_id, quantity FROM orders_detail WHERE orders_id = :id";
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(new IdDto(orderId));
        return jdbcTemplate.query(sql, parameterSource, mapToOrderDetail());
    }

    private RowMapper<OrderDetail> mapToOrderDetail() {
        return (resultSet, rowNum) -> new OrderDetail(
                resultSet.getLong("product_id"),
                resultSet.getInt("quantity")
        );
    }
}

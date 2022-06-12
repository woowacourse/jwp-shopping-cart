package woowacourse.shoppingcart.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.dao.dto.SaveOrderDetailDto;
import woowacourse.shoppingcart.dto.OrderDetailResponse;

import java.util.List;

@Repository
public class OrderDetailDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public OrderDetailDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("orders_detail")
                .usingGeneratedKeyColumns("id");
    }

    private static RowMapper<OrderDetailResponse> rowMapper() {
        return (rs, rowNum) -> new OrderDetailResponse(
                rs.getLong("id"),
                rs.getInt("quantity"),
                rs.getInt("price"),
                rs.getString("name"),
                rs.getString("image_url")
        );
    }

    public long save(long ordersId, long productId, int quantity) {
        SaveOrderDetailDto orderDetail = new SaveOrderDetailDto(ordersId, productId, quantity);
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(orderDetail);
        return simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();
    }

    public List<OrderDetailResponse> findOrdersDetailsByOrderId(long orderId) {
        final String SQL = "SELECT p.id, od.quantity, p.price, p.name, p.image_url " +
                "FROM orders_detail AS od JOIN product AS p ON od.product_id = p.id " +
                "WHERE od.orders_id = ?";
        return jdbcTemplate.query(SQL, rowMapper(), orderId);
    }
}

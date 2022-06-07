package woowacourse.shoppingcart.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Product;

@Repository
public class OrderedProductDao {
    private final JdbcTemplate jdbcTemplate;

    public OrderedProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(final Long ordersId, final Cart cart) {
        final SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("ordered_product")
                .usingGeneratedKeyColumns("id");

        Product product = cart.getProduct();

        Map<String, Object> params = new HashMap<>();
        params.put("orders_id", ordersId);
        params.put("product_id", product.getId());
        params.put("quantity", cart.getQuantity());
        params.put("price", product.getPrice());
        params.put("url", product.getThumbnailImage().getUrl());
        params.put("alt", product.getThumbnailImage().getAlt());

        return simpleJdbcInsert.executeAndReturnKey(params).longValue();
    }

    public List<OrderDetail> findOrdersDetailsByOrderId(final Long orderId) {
        final String sql = "SELECT product_id, quantity FROM ordered_product WHERE orders_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new OrderDetail(
                rs.getLong("product_id"),
                rs.getInt("quantity")
        ), orderId);
    }
}

package woowacourse.shoppingcart.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.OrderedProduct;
import woowacourse.shoppingcart.domain.ThumbnailImage;

@Repository
public class OrderedProductDao {
    private static final RowMapper<OrderedProduct> ORDERED_PRODUCT_ROW_MAPPER = (rs, rowNum) ->
            new OrderedProduct(
                    rs.getLong("product_id"),
                    rs.getInt("quantity"),
                    rs.getInt("price"),
                    rs.getString("name"),
                    new ThumbnailImage(
                            rs.getString("url"),
                            rs.getString("alt")
                    )
            );

    private final JdbcTemplate jdbcTemplate;

    public OrderedProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(final Long ordersId, final CartItem cartItem) {
        final SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("ordered_product")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> params = new HashMap<>();
        params.put("orders_id", ordersId);
        params.put("product_id", cartItem.getProduct().getId());
        params.put("quantity", cartItem.getQuantity());

        return simpleJdbcInsert.executeAndReturnKey(params).longValue();
    }

    public List<OrderedProduct> getAllByOrderId(final Long orderId) {
        final String sql = "SELECT op.product_id, op.quantity, p.price, p.name, t.url, t.alt "
                + "FROM ordered_product as op, product as p, thumbnail_image as t "
                + "WHERE orders_id = ? AND op.product_id = p.id AND t.product_id = p.id";

        return jdbcTemplate.query(sql, ORDERED_PRODUCT_ROW_MAPPER, orderId);
    }
}

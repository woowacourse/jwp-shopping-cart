package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.OrdersDetail;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.NotFoundProductException;

@Repository
public class OrdersDetailDao {

    private final JdbcTemplate jdbcTemplate;
    private final ProductDao productDao;
    private final RowMapper<OrdersDetail> ordersDetailRowMapper = (rs, rowNum) -> new OrdersDetail(
            rs.getLong("id"),
            findProduct(rs.getLong("product_id")),
            rs.getInt("count")
    );

    public OrdersDetailDao(final JdbcTemplate jdbcTemplate, final ProductDao productDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.productDao = productDao;
    }

    public int saveAll(final List<OrdersDetail> ordersDetails, final Long ordersId) {
        final String sql = "INSERT INTO orders_detail(product_id, count, orders_id) values(?, ?, ?)";
        return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(final PreparedStatement ps, final int i) throws SQLException {
                ps.setLong(1, ordersDetails.get(i).getProductId());
                ps.setInt(2, ordersDetails.get(i).getCount());
                ps.setLong(3, ordersId);
            }

            @Override
            public int getBatchSize() {
                return ordersDetails.size();
            }
        }).length;
    }

    public List<OrdersDetail> findDetails(final Long ordersId) {
        final String sql = "SELECT id, product_id, count, orders_id FROM orders_detail WHERE orders_id = ?";

        return jdbcTemplate.query(sql, ordersDetailRowMapper, ordersId);
    }

    private Product findProduct(final Long productId) {
        return productDao.findProductById(productId)
                .orElseThrow(NotFoundProductException::new);
    }
}

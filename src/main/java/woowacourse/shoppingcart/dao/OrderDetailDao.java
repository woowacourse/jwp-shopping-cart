package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import woowacourse.shoppingcart.domain.cart.Quantity;
import woowacourse.shoppingcart.domain.order.OrderDetail;
import woowacourse.shoppingcart.exception.domain.InvalidProductException;

@Repository
public class OrderDetailDao {

    private final ProductDao productDao;
    private final JdbcTemplate jdbcTemplate;

    public OrderDetailDao(ProductDao productDao, JdbcTemplate jdbcTemplate) {
        this.productDao = productDao;
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long addOrderDetail(final Long orderId, final Long productId, final int quantity) {
        final String sql = "INSERT INTO order_detail (order_id, product_id, quantity) VALUES (?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[] {"id"});
            preparedStatement.setLong(1, orderId);
            preparedStatement.setLong(2, productId);
            preparedStatement.setLong(3, quantity);
            return preparedStatement;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public List<OrderDetail> findOrderDetailsByOrderId(final Long orderId) {
        final String sql = "SELECT * FROM order_detail WHERE order_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new OrderDetail(
            rs.getLong("id"),
            new Quantity(rs.getInt("quantity")),
            productDao.findProductById(rs.getLong("product_id"))
                .orElseThrow(InvalidProductException::new)
        ), orderId);
    }

}

package woowacourse.shoppingcart.dao;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.repository.ProductRepository;

@Repository
public class JdbcOrderDetailDao implements OrderDetailDao {
    private final JdbcTemplate jdbcTemplate;
    private final ProductRepository productRepository;

    private final RowMapper<OrderDetail> orderDetailRowMapper = (rs, rowNum) -> {
        Product product = findProductById(rs.getLong("product_id"));
        int quantity = rs.getInt("quantity");
        return new OrderDetail(product, quantity);
    };

    public JdbcOrderDetailDao(JdbcTemplate jdbcTemplate, ProductRepository productRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.productRepository = productRepository;
    }

    @Override
    public void save(long orderId, long productId, int quantity) {
        String sql = "INSERT INTO order_detail(order_id, product_id, quantity) VALUES(?, ?, ?)";
        jdbcTemplate.update(sql, orderId, productId, quantity);
    }

    @Override
    public List<OrderDetail> findAllByOrderId(Long orderId) {
        final String sql = "SELECT product_id, quantity FROM order_detail WHERE order_id = ?";
        return jdbcTemplate.query(sql, orderDetailRowMapper, orderId);
    }

    private Product findProductById(long productId) {
        return productRepository.findById(productId);
    }
}

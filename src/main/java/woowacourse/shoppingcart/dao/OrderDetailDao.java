package woowacourse.shoppingcart.dao;

import static org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import lombok.Getter;
import woowacourse.shoppingcart.domain.OrderDetail;

@Repository
public class OrderDetailDao {

    private final SimpleJdbcInsert jdbcInsert;
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ProductDao productDao;

    public OrderDetailDao(DataSource dataSource, ProductDao productDao) {
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("orders_detail")
            .usingGeneratedKeyColumns("id");
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.productDao = productDao;
    }

    public void saveAll(Long orderId, List<OrderDetail> orderDetails) {
        List<OrderDetailDto> details = orderDetails.stream()
            .map(detail -> new OrderDetailDto(orderId, detail))
            .collect(Collectors.toList());
        jdbcInsert.executeBatch(createBatch(details));
    }

    public List<OrderDetail> findByOrderId(Long orderId) {
        String sql = "select * from orders_detail where orders_id = :orderId";
        return jdbcTemplate.query(sql, Map.of("orderId", orderId), getOrderDetailRowMapper());
    }

    private RowMapper<OrderDetail> getOrderDetailRowMapper() {
        return (rs, rowNum) -> new OrderDetail(
            productDao.findById(rs.getLong("product_id")),
            rs.getInt("quantity")
        );
    }

    @Getter
    private static class OrderDetailDto {
        private final Long ordersId;
        private final Long productId;
        private final int price;
        private final String name;
        private final String imageUrl;
        private final int quantity;

        public OrderDetailDto(Long orderId, OrderDetail orderDetail) {
            this.ordersId = orderId;
            this.productId = orderDetail.getProductId();
            this.price = orderDetail.getPrice();
            this.name = orderDetail.getName();
            this.imageUrl = orderDetail.getImageUrl();
            this.quantity = orderDetail.getQuantity();
        }
    }
}

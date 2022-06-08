package woowacourse.shoppingcart.dao;

import static org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils.*;

import java.util.List;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.codehaus.groovy.ast.tools.ParameterUtils;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import lombok.Getter;
import woowacourse.shoppingcart.domain.OrderDetail;

@Repository
public class OrderDetailDao {

    private final SimpleJdbcInsert jdbcInsert;

    public OrderDetailDao(DataSource dataSource) {
        jdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("orders_detail")
            .usingGeneratedKeyColumns("id");
    }

    public void saveAll(Long orderId, List<OrderDetail> orderDetails) {
        List<OrderDetailDto> details = orderDetails.stream()
            .map(detail -> new OrderDetailDto(orderId, detail))
            .collect(Collectors.toList());
        jdbcInsert.executeBatch(createBatch(details));
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

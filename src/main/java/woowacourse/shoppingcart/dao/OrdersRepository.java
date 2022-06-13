package woowacourse.shoppingcart.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import woowacourse.shoppingcart.dao.dto.OrderDetailDto;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.exception.NoSuchProductException;

@Repository
public class OrdersRepository {

    private final OrdersDetailDao ordersDetailDao;
    private final ProductDao productDao;

    public OrdersRepository(OrdersDetailDao ordersDetailDao, ProductDao productDao) {
        this.ordersDetailDao = ordersDetailDao;
        this.productDao = productDao;
    }

    public List<OrderDetail> findOrdersDetailsByOrderId(Long orderId) {
        List<OrderDetailDto> orderDetailDtos = ordersDetailDao.findOrdersDetailsByOrderId(orderId);
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (OrderDetailDto orderDetailDto : orderDetailDtos) {
            Product product = productDao.findProductById(orderDetailDto.getProductId())
                    .orElseThrow(NoSuchProductException::new);
            orderDetails.add(new OrderDetail(orderDetailDto.getId(), product, orderDetailDto.getQuantity()));
        }
        return orderDetails;
    }
}

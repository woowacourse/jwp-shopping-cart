package woowacourse.shoppingcart.application;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.repository.OrderRepository;
import woowacourse.shoppingcart.repository.dao.CartItemDao;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartItemDao cartItemDao;

    public OrderService(final OrderRepository orderRepository, final CartItemDao cartItemDao) {
        this.orderRepository = orderRepository;
        this.cartItemDao = cartItemDao;
    }

    public Long addOrder(final List<OrderRequest> orderDetailRequests, final Long customerId) {
        for (final OrderRequest orderRequest : orderDetailRequests) {
            orderRepository.create(customerId, cartItemDao.findProductIdById(orderRequest.getId()),
                    orderRequest.getQuantity());
        }
        return customerId;
    }
}

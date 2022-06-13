package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.shoppingcart.fixture.CartItemFixtures.CART_REQUEST_1;
import static woowacourse.shoppingcart.fixture.CartItemFixtures.CART_REQUEST_2;
import static woowacourse.shoppingcart.fixture.CustomerFixtures.CUSTOMER_1;
import static woowacourse.shoppingcart.fixture.ProductFixtures.PRODUCT_1;
import static woowacourse.shoppingcart.fixture.ProductFixtures.PRODUCT_2;

import java.util.List;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.JdbcCustomerDao;
import woowacourse.shoppingcart.dao.JdbcOrderDao;
import woowacourse.shoppingcart.dao.JdbcOrdersDetailDao;
import woowacourse.shoppingcart.dao.JdbcProductDao;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrdersDetailDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.OrderResponse;
import woowacourse.shoppingcart.exception.InvalidOrderException;

@JdbcTest
class OrderServiceTest {
    private final OrderService orderService;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    @Autowired
    public OrderServiceTest(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        final OrderDao orderDao = new JdbcOrderDao(jdbcTemplate);
        final OrdersDetailDao ordersDetailDao = new JdbcOrdersDetailDao(jdbcTemplate);

        this.customerDao = new JdbcCustomerDao(jdbcTemplate, dataSource);
        this.productDao = new JdbcProductDao(jdbcTemplate);
        this.orderService = new OrderService(orderDao, productDao, ordersDetailDao);
    }

    @DisplayName("주문을 추가한다.")
    @Test
    public void addOrders() {
        // given
        final int customerId = customerDao.save(CUSTOMER_1);
        final Long productId1 = productDao.save(PRODUCT_1);
        final Long productId2 = productDao.save(PRODUCT_2);

        // when
        final Long orderId = orderService.addOrders(customerId,
               List.of(new CartRequest(productId1, 3), new CartRequest(productId2, 5)));

        // then
        assertThat(orderId).isPositive();
    }

    @DisplayName("없는 아이템을 추가 시 에외가 발생한다.")
    @Test
    public void addOrdersByNotExistProduct() {
        // given
        final int customerId = customerDao.save(CUSTOMER_1);
        productDao.save(PRODUCT_1);

        // when & then
        assertThatThrownBy(() -> orderService.addOrders(customerId, List.of(CART_REQUEST_1, CART_REQUEST_2)))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("사용자의 주문 전체를 조회한다.")
    @Test
    public void findOrdersByCustomerId() {
        // given
        final int customerId = customerDao.save(CUSTOMER_1);
        final Long productId1 = productDao.save(PRODUCT_1);
        final Long productId2 = productDao.save(PRODUCT_2);

        orderService.addOrders(customerId,
                List.of(new CartRequest(productId1, 3), new CartRequest(productId2, 5)));
        orderService.addOrders(customerId,
                List.of(new CartRequest(productId1, 4), new CartRequest(productId2, 9)));

        // when
        List<OrderResponse>  orderResponses = orderService.findOrdersByCustomerId(customerId);

        // then
        assertAll(
                () -> assertThat(orderResponses).hasSize(2),
                () -> assertThat(orderResponses.stream()
                        .map(OrderResponse::getTotalPrice)
                        .collect(Collectors.toList())).containsExactly(
                        PRODUCT_1.getPrice().getValue() * 3 + PRODUCT_2.getPrice().getValue() * 5,
                        PRODUCT_1.getPrice().getValue() * 4 + PRODUCT_2.getPrice().getValue() * 9)
        );
    }

    @DisplayName("사용자의 단건 주문을 조회한다.")
    @Test
    public void findOrder() {
        // given
        final int customerId = customerDao.save(CUSTOMER_1);
        final Long productId1 = productDao.save(PRODUCT_1);
        final Long productId2 = productDao.save(PRODUCT_2);

        final Long orderId = orderService.addOrders(customerId,
                List.of(new CartRequest(productId1, 3), new CartRequest(productId2, 5)));

        // when
        OrderResponse orderResponse = orderService.findOrder(orderId, customerId);

        // then
        assertAll(
                () -> assertThat(orderResponse.getProducts()).hasSize(2),
                () -> assertThat(orderResponse.getTotalPrice()).isEqualTo(
                        PRODUCT_1.getPrice().getValue() * 3 + PRODUCT_2.getPrice().getValue() * 5)
        );
    }

    @DisplayName("없는 주문 정보를 조회 시 예외 발생")
    @Test
    public void findOrderByInvalidOrder() {
        // given
        final int customerId = customerDao.save(CUSTOMER_1);
        final Long productId1 = productDao.save(PRODUCT_1);
        final Long productId2 = productDao.save(PRODUCT_2);

        final Long orderId = orderService.addOrders(customerId,
                List.of(new CartRequest(productId1, 3), new CartRequest(productId2, 5)));

        // when & then
        assertThatThrownBy(() -> orderService.findOrder(orderId + 1, customerId))
                .isInstanceOf(InvalidOrderException.class);
    }
}
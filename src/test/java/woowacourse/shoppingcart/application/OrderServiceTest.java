package woowacourse.shoppingcart.application;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacourse.shoppingcart.application.dto.OrderDetailsServiceResponse;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrdersDetailDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.NotFoundOrderException;
import woowacourse.shoppingcart.exception.NotFoundProductException;
import woowacourse.shoppingcart.ui.order.dto.request.OrderRequest;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    private final Long CUSTOMER_ID = 1L;
    private final Long ORDER_ID = 1L;
    private final Long CART_ID = 1L;
    private final Long PRODUCT_ID = 1L;
    private final int QUANTITY = 10;

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderDao orderDao;

    @Mock
    private OrdersDetailDao ordersDetailDao;

    @Mock
    private CartItemDao cartItemDao;

    @Mock
    private ProductDao productDao;

    @Test
    @DisplayName("장바구니 상품들을 주문한다.")
    void addOrder() {
        //given
        final OrderRequest orderRequest = new OrderRequest(CART_ID, QUANTITY);

        when(orderDao.saveOrders(CUSTOMER_ID))
                .thenReturn(ORDER_ID);
        when(cartItemDao.findProductIdById(CART_ID))
                .thenReturn(Optional.of(PRODUCT_ID));

        //when, then
        assertThatCode(() -> orderService.addOrder(CUSTOMER_ID, List.of(orderRequest)))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("주문시 존재하지 않는 상품일 경우 예외를 던진다.")
    void addOrder_invalidProduct_throwsException() {
        //given
        final OrderRequest orderRequest = new OrderRequest(CART_ID, QUANTITY);

        when(orderDao.saveOrders(CUSTOMER_ID))
                .thenReturn(ORDER_ID);
        when(cartItemDao.findProductIdById(CART_ID))
                .thenReturn(Optional.empty());

        //when, then
        assertThatThrownBy(() -> orderService.addOrder(CUSTOMER_ID, List.of(orderRequest)))
                .isInstanceOf(NotFoundProductException.class);
    }

    @Test
    @DisplayName("단일 주문을 조회한다.")
    void findOrderById() {
        //given
        final String name = "치킨";
        final int price = 10_000;
        final String imageUrl = "http://example.com/chicken.jpg";

        when(orderDao.isValidOrderId(CUSTOMER_ID, ORDER_ID))
                .thenReturn(true);
        when(ordersDetailDao.findOrdersDetailsByOrderId(ORDER_ID))
                .thenReturn(
                        List.of(new OrderDetail(PRODUCT_ID, QUANTITY)));
        when(productDao.findProductById(PRODUCT_ID))
                .thenReturn(Optional.of(new Product(PRODUCT_ID, name, price, imageUrl)));

        //when
        final OrderDetailsServiceResponse actual = orderService.findOrderById(CUSTOMER_ID, ORDER_ID);

        //then
        assertThat(actual.getId()).isEqualTo(ORDER_ID);
        assertThat(actual.getTotalPrice()).isEqualTo(price);
        assertThat(actual.getOrderDetails().size()).isOne();
    }

    @Test
    @DisplayName("단일 주문을 조회한다.")
    void findOrderById_invalidOrder_throwsException() {
        //given
        when(orderDao.isValidOrderId(CUSTOMER_ID, ORDER_ID))
                .thenReturn(false);

        //when, then
        assertThatThrownBy(() -> orderService.findOrderById(CUSTOMER_ID, ORDER_ID))
                .isInstanceOf(NotFoundOrderException.class);
    }

    @Test
    @DisplayName("주문 목록을 조회한다.")
    void findOrdersByCustomerId() {
        //given
        final String name = "치킨";
        final int price = 10_000;
        final String imageUrl = "http://example.com/chicken.jpg";
        when(orderDao.findOrderIdsByCustomerId(CUSTOMER_ID))
                .thenReturn(List.of(1L));
        when(ordersDetailDao.findOrdersDetailsByOrderId(ORDER_ID))
                .thenReturn(List.of(new OrderDetail(PRODUCT_ID, QUANTITY)));
        when(productDao.findProductById(PRODUCT_ID))
                .thenReturn(Optional.of(new Product(PRODUCT_ID, name, price, imageUrl)));

        //when
        final List<OrderDetailsServiceResponse> actual = orderService.findOrdersByCustomerId(CUSTOMER_ID);

        //then
        assertThat(actual.size()).isOne();
    }
}

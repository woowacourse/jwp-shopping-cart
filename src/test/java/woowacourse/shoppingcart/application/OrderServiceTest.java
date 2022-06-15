package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static woowacourse.ShoppingCartFixture.잉_회원생성요청;
import static woowacourse.ShoppingCartFixture.제품_추가_요청1;
import static woowacourse.ShoppingCartFixture.제품_추가_요청2;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.exception.notFound.InvalidOrderException;
import woowacourse.shoppingcart.dto.request.CartAddRequest;
import woowacourse.shoppingcart.dto.request.OrderRequest;
import woowacourse.shoppingcart.dto.response.OrderDetailResponse;
import woowacourse.shoppingcart.dto.response.OrdersResponse;

@SpringBootTest
@TestConstructor(autowireMode = AutowireMode.ALL)
@Sql("/truncate.sql")
class OrderServiceTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    private Long customerId;

    private Long cartId1;

    private Long cartId2;

    @BeforeEach
    void setUp() {
        customerId = customerService.create(잉_회원생성요청);
        final Long product1 = productService.addProduct(제품_추가_요청1);
        final Long product2 = productService.addProduct(제품_추가_요청2);
        cartId1 = cartService.addCart(customerId, new CartAddRequest(product1));
        cartId2 = cartService.addCart(customerId, new CartAddRequest(product2));
    }


    @DisplayName("주문을 추가할 수 있다.")
    @Test
    void addOrder() {
        List<OrderRequest> orderRequests = List.of(
                new OrderRequest(cartId1, 1),
                new OrderRequest(cartId2, 2)
        );

        assertThat(orderService.addOrder(orderRequests, customerId))
                .isEqualTo(1L);
    }

    @DisplayName("단일 주문을 조회할 수 있다.")
    @Test
    void findOrderById() {
        List<OrderRequest> orderRequests = List.of(
                new OrderRequest(cartId1, 1)
        );

        final Long orderId = orderService.addOrder(orderRequests, customerId);
        final OrdersResponse 주문_조회함 = orderService.findOrderById(customerId, orderId);
        final OrderDetailResponse 주문_조회_첫번쨰 = 주문_조회함.getOrderDetails().get(0);

        assertThat(주문_조회함.getId()).isEqualTo(orderId);
        assertThat(주문_조회함.getTotalPrice()).isEqualTo(20000);
        assertThat(주문_조회_첫번쨰.getProductId()).isEqualTo(1L);
        assertThat(주문_조회_첫번쨰.getQuantity()).isEqualTo(1);
        assertThat(주문_조회_첫번쨰.getPrice()).isEqualTo(제품_추가_요청1.getPrice());
        assertThat(주문_조회_첫번쨰.getName()).isEqualTo(제품_추가_요청1.getName());
        assertThat(주문_조회_첫번쨰.getImageUrl()).isEqualTo(제품_추가_요청1.getImageUrl());
    }

    @DisplayName("존재하지 않는 주문을 조히하는 경우, 주문을 단일 조회 할 수 없다.")
    @Test
    void findOrderByIdWithNotFoundOrder() {
        List<OrderRequest> orderRequests = List.of(
                new OrderRequest(cartId1, 1)
        );
        final Long orderId = orderService.addOrder(orderRequests, customerId);

        assertThatThrownBy(() -> orderService.findOrderById(customerId, orderId + 1L))
                .isInstanceOf(InvalidOrderException.class);
    }

    @DisplayName("전체 주문을 조회할 수 있다.")
    @Test
    void findOrdersByCustomerId() {
        List<OrderRequest> orderRequests1 = List.of(
                new OrderRequest(cartId1, 1)
        );

        List<OrderRequest> orderRequests2 = List.of(
                new OrderRequest(cartId2, 1)
        );
        orderService.addOrder(orderRequests1, customerId);
        orderService.addOrder(orderRequests2, customerId);

        final List<OrdersResponse> 주문_전체_조회함 = orderService.findOrdersByCustomerId(customerId);

        assertThat(주문_전체_조회함.size()).isEqualTo(2);
    }
}

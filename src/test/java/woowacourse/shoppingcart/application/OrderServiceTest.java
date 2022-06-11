package woowacourse.shoppingcart.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class OrderServiceTest {

    @BeforeEach
    void setUp() {
        //given
        SignUpRequest signUpRequest = new SignUpRequest("greenlawn", "green@woowa.com", "123456q!");
        customerService.addCustomer(signUpRequest);

        ProductRequest productRequest = new ProductRequest("피자", 20000, "http://example.com/pizza.jpg");
        ProductRequest productRequest2 = new ProductRequest("치킨", 20000, "http://example.com/chicken.jpg");
        ProductRequest productRequest3 = new ProductRequest("국수", 20000, "http://example.com/noodle.jpg");

        ProductResponse product1 = productService.addProduct(productRequest);
        ProductResponse product2 = productService.addProduct(productRequest2);
        ProductResponse product3 = productService.addProduct(productRequest3);

        cartService.addCart(new CartProductRequest(product1.getId(), 1L, true), "greenlawn");
        cartService.addCart(new CartProductRequest(product2.getId(), 1L, true), "greenlawn");
        cartService.addCart(new CartProductRequest(product3.getId(), 1L, true), "greenlawn");
    }

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @Test
    @DisplayName("주문을 추가한다.")
    void addOrder() {
        //when & then
        List<OrderRequest> orderRequests = Stream.of(1L, 2L)
                .map(cartId -> new OrderRequest(cartId, 10))
                .collect(Collectors.toList());
        assertDoesNotThrow(() -> orderService.addOrder(orderRequests, "greenlawn"));
    }

    @Test
    @DisplayName("주문 단일 조회")
    void getOrder() {
        //when
        List<OrderRequest> orderRequests = Stream.of(1L, 2L)
                .map(cartId -> new OrderRequest(cartId, 10))
                .collect(Collectors.toList());
        orderService.addOrder(orderRequests, "greenlawn");

        List<OrderRequest> orderRequests2 = Stream.of(3L)
                .map(cartId -> new OrderRequest(cartId, 10))
                .collect(Collectors.toList());
        orderService.addOrder(orderRequests2, "greenlawn");

        //then
        assertThat(orderService.findOrderById("greenlawn", 2L)
                .getOrderDetails()
                .size())
                .isEqualTo(1L);
    }

    @Test
    @DisplayName("주문 목록 조회")
    void getOrders() {
        //when
        List<OrderRequest> orderRequests = Stream.of(1L, 2L)
                .map(cartId -> new OrderRequest(cartId, 10))
                .collect(Collectors.toList());
        orderService.addOrder(orderRequests, "greenlawn");

        List<OrderRequest> orderRequests2 = Stream.of(3L)
                .map(cartId -> new OrderRequest(cartId, 10))
                .collect(Collectors.toList());
        orderService.addOrder(orderRequests2, "greenlawn");

        //then
        assertThat(orderService.findOrdersByCustomerName("greenlawn").size()).isEqualTo(2L);
    }
}
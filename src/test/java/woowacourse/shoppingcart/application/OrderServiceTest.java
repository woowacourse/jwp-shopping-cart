package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;

import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.order.Order;
import woowacourse.shoppingcart.domain.order.OrderItem;
import woowacourse.shoppingcart.exception.InvalidOrderException;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class OrderServiceTest {

    private static final long CUSTOMER_ID = 1L;

    private final OrderService orderService;
    private final CartService cartService;
    private final ProductService productService;

    private final OrderDao orderDao;
    private final CartItemDao cartItemDao;
    private final ProductDao productDao;

    public OrderServiceTest(JdbcTemplate jdbcTemplate) {
        this.productDao = new ProductDao(jdbcTemplate);
        this.cartItemDao = new CartItemDao(jdbcTemplate);
        this.orderDao = new OrderDao(jdbcTemplate);

        this.cartService = new CartService(cartItemDao, productDao);
        this.productService = new ProductService(productDao);
        this.orderService = new OrderService(cartService, productService, orderDao);
    }

    @BeforeEach
    void setUp() {
        cartService.addCartItem(1L, CUSTOMER_ID, 3);
        cartService.addCartItem(2L, CUSTOMER_ID, 4);
    }

    @Test
    @DisplayName("해당 고객 id의 장바구니가 존재하지 않을 경우 예외를 반환한다.")
    void save_customerNotFound() {
        //when, then
        Assertions.assertThatThrownBy(() -> orderService.save(99L))
            .isInstanceOf(InvalidOrderException.class)
            .hasMessage("해당 고객의 장바구니가 존재하지 않습니다.");
    }

    @Test
    @DisplayName("주문을 저장한다. 주문한 상품 재고가 감소함을 검증한다.")
    void save() {
        //when
        Long orderId = orderService.save(CUSTOMER_ID);

        //then
        int quantityOfProduct1 = productService.findProductById(1L).getQuantity();
        int quantityOfProduct2 = productService.findProductById(2L).getQuantity();
        List<OrderItem> orderItems = orderDao.findOrderItemsByOrderId(orderId);
        List<OrderItem> expectedOrderDetail = List.of(new OrderItem(1L, 3), new OrderItem(2L, 4));

        assertAll(
            () -> assertThat(orderItems).usingRecursiveComparison()
                .isEqualTo(expectedOrderDetail),
            () -> assertThat(quantityOfProduct1).isEqualTo(7),
            () -> assertThat(quantityOfProduct2).isEqualTo(6)
        );
    }

    @Test
    @DisplayName("주문 내역 전체를 조회한다.")
    void findAll() {
        //given
        orderService.save(CUSTOMER_ID);

        //when
        List<Order> orders = orderService.findAll(CUSTOMER_ID);

        //then
        List<Order> expected = List.of(new Order(CUSTOMER_ID, List.of(new OrderItem(1L, 3),
            new OrderItem(2L, 4))));

        assertThat(orders).usingRecursiveComparison()
            .ignoringExpectedNullFields()
            .isEqualTo(expected);
    }

    @Test
    @DisplayName("단일 주문 내역을 조회한다.")
    void findOne() {
        //given
        Long orderId = orderService.save(CUSTOMER_ID);

        //when
        Order order = orderService.findOne(CUSTOMER_ID, orderId);

        //then
        Order expected = new Order(CUSTOMER_ID, List.of(new OrderItem(1L, 3),
            new OrderItem(2L, 4)));

        assertThat(order).usingRecursiveComparison()
            .ignoringExpectedNullFields()
            .isEqualTo(expected);
    }
}

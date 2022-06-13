package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;

import woowacourse.shoppingcart.domain.cart.Cart;
import woowacourse.shoppingcart.domain.cart.CartItem;
import woowacourse.shoppingcart.domain.order.Order;
import woowacourse.shoppingcart.domain.order.OrderItem;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class OrderDaoTest {

    private CartItem cartItem1;
    private CartItem cartItem2;

    private final OrderDao orderDao;
    private final CartItemDao cartItemDao;
    private final ProductDao productDao;

    public OrderDaoTest(JdbcTemplate jdbcTemplate) {
        this.orderDao = new OrderDao(jdbcTemplate);
        this.productDao = new ProductDao(jdbcTemplate);
        this.cartItemDao = new CartItemDao(jdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        cartItem1 = getCartItem(1L, 1L, 3);
        cartItem2 = getCartItem(2L, 2L, 4);
        cartItemDao.addCartItem(1L, cartItem1);
        cartItemDao.addCartItem(1L, cartItem2);
    }

    private CartItem getCartItem(long cartItemId, long productId, int count) {
        return new CartItem(cartItemId, count, productDao.findProductById(productId));
    }

    @Test
    @DisplayName("주문을 저장한다.")
    void save() {
        //when
        Order order = new Order(1L, new Cart(List.of(cartItem1, cartItem2)));
        Long orderId = orderDao.save(order);

        //then
        List<OrderItem> orderItems = orderDao.findOrderItemsByOrderId(orderId);
        List<OrderItem> expected = List.of(new OrderItem(1L, 3), new OrderItem(2L, 4));

        assertThat(orderItems).usingRecursiveComparison()
            .isEqualTo(expected);
    }

    @Test
    @DisplayName("주문 내역 전체를 조회한다.")
    void findAll() {
        //given
        Order order = new Order(1L, new Cart(List.of(cartItem1, cartItem2)));
        orderDao.save(order);

        //when
        List<Order> orders = orderDao.findAllByCustomerId(1L);

        //then
        List<Order> expected = List.of(new Order(1L, List.of(new OrderItem(1L, 3),
            new OrderItem(2L, 4))));
        assertThat(orders).usingRecursiveComparison()
            .isEqualTo(expected);
    }

    @Test
    @DisplayName("단일 주문의 주문 상품 목록 전체를 조회한다.")
    void findOrderDetailsByOrderId() {
        //given
        Order order = new Order(1L, new Cart(List.of(cartItem1, cartItem2)));
        Long orderId = orderDao.save(order);

        //when
        List<OrderItem> orderItems = orderDao.findOrderItemsByOrderId(orderId);

        //then
        List<OrderItem> expected = List.of(new OrderItem(1L, 3),
            new OrderItem(2L, 4));

        assertThat(orderItems).usingRecursiveComparison()
            .isEqualTo(expected);
    }
}

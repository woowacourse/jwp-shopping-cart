package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.OrdersDetailDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.OrderResponse;
import woowacourse.shoppingcart.dto.ThumbnailImage;
import woowacourse.shoppingcart.exception.OutOfStockException;

@SpringBootTest
@Sql(scripts = "classpath:truncate.sql")
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private CartItemDao cartItemDao;

    @Autowired
    private OrdersDetailDao ordersDetailDao;

    private static final String EMAIL = "test@gmail.com";

    private Long customerId;
    private Long cartItemId1;
    private Long cartItemId2;

    @BeforeEach
    void setUp(){
        customerDao.save(new Customer(EMAIL, "password1!", "이스트"));
        customerId = customerDao.findByEmail(EMAIL).getId();
        final Long 맥북Id = productDao.save(new Product("맥북", 250, 10L,
                new ThumbnailImage("test.com", "이미지입니다."))).getId();
        final Long 애플워치Id = productDao.save(new Product("애플워치", 250, 10L,
                new ThumbnailImage("test.com", "이미지입니다."))).getId();
        cartItemId1 = cartItemDao.addCartItem(customerId, 맥북Id, 5);
        cartItemId2 = cartItemDao.addCartItem(customerId, 애플워치Id, 5);
    }


    @DisplayName("주문을 추가하는 기능")
    @Test
    void addOrder() {
        //when
        final Long orderId = orderService.addOrder(List.of(cartItemId1, cartItemId2), EMAIL);
        //then
        final List<OrderDetail> orderDetails = ordersDetailDao.findOrdersDetailsByOrderId(orderId);
        final List<String> orderedProductsName = orderDetails.stream()
                .map(OrderDetail::getProduct)
                .map(Product::getName)
                .collect(Collectors.toUnmodifiableList());
        assertThat(orderedProductsName).containsExactly("맥북", "애플워치");
    }

    @DisplayName("재고보다 많은 수량을 주문하면 예외 발생")
    @Test
    void addOutOfStockOrder() {
        //given
        cartItemDao.updateQuantityByCustomerId(customerId, cartItemId1, 11);
        //then
        assertThatThrownBy(() -> orderService.addOrder(List.of(cartItemId1), EMAIL))
                .isInstanceOf(OutOfStockException.class);
    }

    @DisplayName("이메일을 기반으로 주문들을 찾아오는 기능")
    @Test
    void findOrdersByEmail() {
        //given
        final Long orderId = orderService.addOrder(List.of(cartItemId1, cartItemId2), EMAIL);
        //when
        final OrderResponse orderResponse = orderService.findOrdersByEmail(EMAIL).get(0);
        //then
        assertThat(orderResponse.getId()).isEqualTo(orderId);
    }
}

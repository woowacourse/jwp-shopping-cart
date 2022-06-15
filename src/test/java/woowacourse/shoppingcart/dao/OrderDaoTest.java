package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestConstructor;

import woowacourse.auth.dao.CustomerDao;
import woowacourse.auth.domain.Customer;
import woowacourse.shoppingcart.ProductInsertUtil;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.domain.Product;

@JdbcTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class OrderDaoTest {

    private Long customerId;
    private CartItem cartItem1;
    private CartItem cartItem2;
    private CartItem cartItem3;

    private final OrderDao orderDao;
    private final ProductInsertUtil productInsertUtil;
    private final CustomerDao customerDao;
    private final CartItemDao cartItemDao;

    public OrderDaoTest(DataSource dataSource) {
        ProductDao productDao = new ProductDao(dataSource);
        this.orderDao = new OrderDao(dataSource, new OrderDetailDao(dataSource, productDao));
        productInsertUtil = new ProductInsertUtil(dataSource);
        customerDao = new CustomerDao(dataSource);
        cartItemDao = new CartItemDao(dataSource, productDao);
    }

    @BeforeEach
    void setUp() {
        Product product1 = productInsertUtil.insertAndReturn("치킨", 20000, "https://test.jpg");
        Product product2 = productInsertUtil.insertAndReturn("콜라", 1500, "https://test.jpg");
        Product product3 = productInsertUtil.insertAndReturn("피자", 1500, "https://test.jpg");
        customerId = customerDao.save(Customer.builder()
            .email("abc@gmail.com")
            .password("a1234!")
            .nickname("does")
            .build())
            .getId();
        cartItem1 = cartItemDao.save(customerId, new CartItem(product1, 2));
        cartItem2 = cartItemDao.save(customerId, new CartItem(product2, 2));
        cartItem3 = cartItemDao.save(customerId, new CartItem(product3, 2));
    }

    @DisplayName("주문을 저장한다.")
    @Test
    void save() {
        // given
        Orders orders = new Orders(customerId, List.of(cartItem1, cartItem2, cartItem3));

        // when
        Orders saved = orderDao.save(orders);

        // then
        assertThat(saved.getId()).isNotNull();
    }

    @DisplayName("주문을 조회한다.")
    @Test
    void find() {
        // given
        Orders orders = new Orders(customerId, List.of(cartItem1, cartItem2, cartItem3));
        Orders saved = orderDao.save(orders);

        // when
        Orders found = orderDao.findById(saved.getId());

        // then
        assertThat(saved).usingRecursiveComparison().isEqualTo(found);
    }
}

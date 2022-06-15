package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.helper.fixture.MemberFixture.EMAIL;
import static woowacourse.helper.fixture.MemberFixture.NAME;
import static woowacourse.helper.fixture.MemberFixture.PASSWORD;
import static woowacourse.helper.fixture.MemberFixture.createMember;
import static woowacourse.helper.fixture.ProductFixture.createProduct;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.member.dao.MemberDao;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("classpath:schema.sql")
class OrdersDetailDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private CartItemDao cartItemDao;
    private ProductDao productDao;
    private MemberDao memberDao;
    private OrderDao orderDao;
    private OrdersDetailDao ordersDetailDao;

    @BeforeEach
    void setUp() {
        cartItemDao = new CartItemDao(jdbcTemplate);
        productDao = new ProductDao(jdbcTemplate);
        memberDao = new MemberDao(jdbcTemplate);
        orderDao = new OrderDao(jdbcTemplate);
        ordersDetailDao = new OrdersDetailDao(jdbcTemplate);
    }

    @DisplayName("OrderDatail을 추가하는 기능")
    @Test
    void addOrdersDetail() {
        Long memberId = memberDao.save(createMember(EMAIL, PASSWORD, NAME));
        Long orderId = orderDao.addOrders(memberId);
        final Long productId = productDao.save(createProduct("초콜렛", 1_000, "www.test.com"));

        Long orderDetailId = ordersDetailDao.addOrdersDetail(orderId, productId, 5);

        assertThat(orderDetailId).isEqualTo(1L);
    }

    @DisplayName("OrderId로 OrderDetails 조회하는 기능")
    @Test
    void findOrdersDetailsByOrderId() {
        Long memberId = memberDao.save(createMember(EMAIL, PASSWORD, NAME));
        Long orderId = orderDao.addOrders(memberId);
        final Long productId = productDao.save(createProduct("초콜렛", 1_000, "www.test.com"));

        ordersDetailDao.addOrdersDetail(orderId, productId, 5);

        assertThat(ordersDetailDao.findOrdersDetailsByOrderId(orderId)).hasSize(1);
    }
}

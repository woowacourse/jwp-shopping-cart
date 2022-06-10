package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.helper.fixture.MemberFixture.EMAIL;
import static woowacourse.helper.fixture.MemberFixture.NAME;
import static woowacourse.helper.fixture.MemberFixture.PASSWORD;
import static woowacourse.helper.fixture.MemberFixture.createMember;
import static woowacourse.helper.fixture.ProductFixture.PRODUCT_IMAGE;
import static woowacourse.helper.fixture.ProductFixture.PRODUCT_NAME;
import static woowacourse.helper.fixture.ProductFixture.PRODUCT_PRICE;
import static woowacourse.helper.fixture.ProductFixture.createProduct;

import java.util.Collections;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import woowacourse.helper.annotations.DaoTest;
import woowacourse.member.dao.MemberDao;
import woowacourse.member.dto.OrderSaveServiceRequest;
import woowacourse.shoppingcart.domain.LazyOrders;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Orders;

@DaoTest
class OrderDaoTest {

    private final OrderDao orderDao;
    private final OrdersDetailDao ordersDetailDao;
    private final ProductDao productDao;
    private final MemberDao memberDao;

    public OrderDaoTest(DataSource dataSource) {
        this.orderDao = new OrderDao(dataSource);
        this.ordersDetailDao = new OrdersDetailDao(dataSource);
        this.productDao = new ProductDao(new JdbcTemplate(dataSource));
        this.memberDao = new MemberDao(dataSource);
    }

    @DisplayName("Order를 추가하는 기능")
    @Test
    void addOrders() {
        final Long id = memberDao.save(createMember(EMAIL, PASSWORD, NAME));
        final Long orderId = orderDao.save(new OrderSaveServiceRequest(id));

        assertThat(orderId).isNotNull();
    }

    @DisplayName("레이지 로딩된 Order를 가져온다.")
    @Test
    void findLazyOrder() {
        final Long id = memberDao.save(createMember(EMAIL, PASSWORD, NAME));
        final Long productId = productDao.save(createProduct(PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_IMAGE));

        final Long orderId = orderDao.save(new OrderSaveServiceRequest(id));
        ordersDetailDao.addBatchOrderDetails(Collections.singletonList(
                new OrderDetail(orderId, productId, 10))
        );

        final Orders order = orderDao.findOrderByIdLazyOrderDetails(orderId);
        assertAll(
                () -> assertThat(order).isInstanceOf(LazyOrders.class),
                () -> assertThat(order.getOrderDetails()).hasSize(1)
        );
    }

    @DisplayName("Order가 해당 멤버의 주문인지 확인")
    @Test
    void isValid() {
        final Long id = memberDao.save(createMember(EMAIL, PASSWORD, NAME));
        final Long orderId = orderDao.save(new OrderSaveServiceRequest(id));

        assertThat(orderDao.isValidOrderId(id, orderId)).isTrue();
    }

    @DisplayName("레이지 로딩된 Order들을 가져온다.")
    @Test
    void findLazyOrdersByMemberId() {
        final Long id = memberDao.save(createMember(EMAIL, PASSWORD, NAME));
        final Long productId = productDao.save(createProduct(PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_IMAGE));

        final Long orderId = orderDao.save(new OrderSaveServiceRequest(id));
        ordersDetailDao.addBatchOrderDetails(Collections.singletonList(
                new OrderDetail(orderId, productId, 10))
        );

        final Long orderId2 = orderDao.save(new OrderSaveServiceRequest(id));
        ordersDetailDao.addBatchOrderDetails(Collections.singletonList(
                new OrderDetail(orderId2, productId, 20))
        );

        final List<Orders> orders = orderDao.findOrdersByIdLazyOrderDetails(id);
        assertAll(
                () -> assertThat(orders).hasSize(2)
        );
    }
}

package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.helper.fixture.MemberFixture.EMAIL;
import static woowacourse.helper.fixture.MemberFixture.NAME;
import static woowacourse.helper.fixture.MemberFixture.PASSWORD;
import static woowacourse.helper.fixture.MemberFixture.createMember;
import static woowacourse.helper.fixture.ProductFixture.PRODUCT_IMAGE;
import static woowacourse.helper.fixture.ProductFixture.PRODUCT_NAME;
import static woowacourse.helper.fixture.ProductFixture.PRODUCT_PRICE;
import static woowacourse.helper.fixture.ProductFixture.createProduct;

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
import woowacourse.member.domain.Member;
import woowacourse.member.dto.OrderSaveServiceRequest;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Product;

@DaoTest
public class OrdersDetailDaoTest {

    private final OrdersDetailDao ordersDetailDao;
    private final MemberDao memberDao;
    private final OrderDao orderDao;
    private final ProductDao productDao;

    public OrdersDetailDaoTest(DataSource dataSource) {
        this.ordersDetailDao = new OrdersDetailDao(dataSource);
        this.memberDao = new MemberDao(dataSource);
        this.orderDao = new OrderDao(dataSource);
        this.productDao = new ProductDao(new JdbcTemplate(dataSource));
    }

    @DisplayName("OrderDetail을 배치로 업데이트한다.")
    @Test
    void batchInsert() {
        final Long memberId = saveMember();
        final Long productId = saveProduct(PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_IMAGE);
        final Long productId2 = saveProduct("김치볶음밥", 5000, "url.com");
        final Long orderId = orderDao.save(new OrderSaveServiceRequest(memberId));

        final OrderDetail orderDetail = new OrderDetail(orderId, productId, 10);
        final OrderDetail orderDetail2 = new OrderDetail(orderId, productId2, 10);

        ordersDetailDao.addBatchOrderDetails(List.of(orderDetail, orderDetail2));
        assertThat(orderDao.findOrderByIdLazyOrderDetails(orderId).getOrderDetails()).hasSize(2);
    }

    private Long saveMember() {
        final Member member = createMember(EMAIL, PASSWORD, NAME);
        return memberDao.save(member);
    }

    private Long saveProduct(final String name, final int price, final String url) {
        final Product product = createProduct(name, price, url);
        return productDao.save(product);
    }
}

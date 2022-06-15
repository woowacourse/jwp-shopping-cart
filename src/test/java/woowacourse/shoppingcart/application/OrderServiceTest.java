package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.helper.fixture.MemberFixture.EMAIL;
import static woowacourse.helper.fixture.MemberFixture.NAME;
import static woowacourse.helper.fixture.MemberFixture.PASSWORD;
import static woowacourse.helper.fixture.MemberFixture.createMember;
import static woowacourse.helper.fixture.ProductFixture.IMAGE;
import static woowacourse.helper.fixture.ProductFixture.PRICE;
import static woowacourse.helper.fixture.ProductFixture.createProduct;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.helper.fixture.ProductFixture;
import woowacourse.member.dao.MemberDao;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.dto.OrderRequest;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @Autowired
    ProductDao productDao;

    @Autowired
    MemberDao memberDao;

    @Autowired
    CartItemDao cartItemDao;

    @DisplayName("주문을 추가한다.")
    @Test
    void save() {
        Long productId = productDao.save(createProduct(ProductFixture.NAME, PRICE, IMAGE));
        Long memberId = memberDao.save(createMember(EMAIL, PASSWORD, NAME));
        Long cartId = cartItemDao.addCartItem(memberId, productId);

        List<OrderRequest> orderRequests = List.of(new OrderRequest(cartId));
        Long orderId = orderService.addOrder(orderRequests, memberId);

        assertThat(orderId).isNotNull();
    }

    @DisplayName("주문 하나를 조회한다.")
    @Test
    void findOrderById() {
        Long productId = productDao.save(createProduct(ProductFixture.NAME, PRICE, IMAGE));
        Long memberId = memberDao.save(createMember(EMAIL, PASSWORD, NAME));
        Long cartId = cartItemDao.addCartItem(memberId, productId);

        List<OrderRequest> orderRequests = List.of(new OrderRequest(cartId));
        Long orderId = orderService.addOrder(orderRequests, memberId);

        assertThat(orderService.findOrderById(memberId, orderId).getOrderDetails()).isNotEmpty();
    }

    @DisplayName("주문을 모두 조회한다.")
    @Test
    void findOrdersByMemberId() {
        Long productId = productDao.save(createProduct(ProductFixture.NAME, PRICE, IMAGE));
        Long memberId = memberDao.save(createMember(EMAIL, PASSWORD, NAME));
        Long cartId = cartItemDao.addCartItem(memberId, productId);

        List<OrderRequest> orderRequests = List.of(new OrderRequest(cartId));
        orderService.addOrder(orderRequests, memberId);

        assertThat(orderService.findOrdersByMemberId(memberId).size()).isEqualTo(1);
    }
}

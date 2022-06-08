package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.helper.fixture.MemberFixture.EMAIL;
import static woowacourse.helper.fixture.MemberFixture.NAME;
import static woowacourse.helper.fixture.MemberFixture.PASSWORD;
import static woowacourse.helper.fixture.ProductFixture.PRODUCT_IMAGE;
import static woowacourse.helper.fixture.ProductFixture.PRODUCT_NAME;
import static woowacourse.helper.fixture.ProductFixture.PRODUCT_PRICE;
import static woowacourse.helper.fixture.ProductFixture.createProduct;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.helper.fixture.MemberFixture;
import woowacourse.member.dao.MemberDao;
import woowacourse.shoppingcart.dao.CartDao;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.OrderRequest;

@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private CartDao cartDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderService orderService;

    @DisplayName("주문한다.")
    @Test
    void addOrder() {
        final Long productId = productDao.save(createProduct(PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_IMAGE));
        final Long memberId = memberDao.save(MemberFixture.createMember(EMAIL, PASSWORD, NAME));
        final Long cartId1 = cartDao.save(
                new Cart(memberId, new Product(productId, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_IMAGE), 1));
        final Long cartId2 = cartDao.save(
                new Cart(memberId, new Product(productId, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_IMAGE), 1));
        final Long cartId3 = cartDao.save(
                new Cart(memberId, new Product(productId, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_IMAGE), 1));
        orderService.addOrder(memberId, List.of(new OrderRequest(cartId1), new OrderRequest(cartId2), new OrderRequest(cartId3)));

        assertAll(
                () -> assertThat(orderDao.findOrdersByIdLazyOrderDetails(memberId)).hasSize(1),
                () -> assertThat(cartDao.findCartsByMemberId(memberId)).hasSize(0)
        );
    }

}

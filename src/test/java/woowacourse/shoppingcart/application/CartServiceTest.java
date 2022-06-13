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
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.CartRequest;

@SpringBootTest
@Transactional
public class CartServiceTest {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private CartDao cartDao;

    @Autowired
    private CartService cartService;

    @DisplayName("제품이 카트에 존재하는 경우 수량을 추가한다.")
    @Test
    void updateCart() {
        final Long productId = productDao.save(createProduct(PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_IMAGE));
        final Long memberId = memberDao.save(MemberFixture.createMember(EMAIL, PASSWORD, NAME));
        cartDao.save(new Cart(memberId, new Product(productId, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_IMAGE), 1));

        final CartRequest cartRequest = new CartRequest(productId);
        cartService.addCart(memberId, cartRequest);
        final List<Cart> carts = cartDao.findCartsByMemberId(memberId);
        assertAll(
                () -> assertThat(carts.get(0).getQuantity()).isEqualTo(2),
                () -> assertThat(carts).hasSize(1)
        );

    }

    @DisplayName("제품이 없는 경우 새로 추가한다.")
    @Test
    void addCart() {
        final Long productId = productDao.save(createProduct(PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_IMAGE));
        final Long memberId = memberDao.save(MemberFixture.createMember(EMAIL, PASSWORD, NAME));

        final CartRequest cartRequest = new CartRequest(productId);
        cartService.addCart(memberId, cartRequest);
        assertThat(cartDao.findCartsByMemberId(memberId)).hasSize(1);
    }
}

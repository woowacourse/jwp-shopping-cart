package woowacourse.shoppingcart.dao;


import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.helper.fixture.MemberFixture.EMAIL;
import static woowacourse.helper.fixture.MemberFixture.NAME;
import static woowacourse.helper.fixture.MemberFixture.PASSWORD;
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
import org.springframework.test.context.jdbc.Sql;
import woowacourse.helper.annotations.DaoTest;
import woowacourse.helper.fixture.MemberFixture;
import woowacourse.member.dao.MemberDao;
import woowacourse.shoppingcart.domain.Cart;

@DaoTest
public class CartDaoTest {

    private CartDao cartDao;

    private MemberDao memberDao;

    private ProductDao productDao;

    public CartDaoTest(final DataSource dataSource) {
        cartDao = new CartDao(dataSource);
        memberDao = new MemberDao(dataSource);
        productDao = new ProductDao(new JdbcTemplate(dataSource));
    }

    @DisplayName("카트를 저장한다.")
    @Test
    void createCarts() {
        final Long memberId = memberDao.save(MemberFixture.createMember(EMAIL, PASSWORD, NAME));
        final Long productId = productDao.save(createProduct(PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_IMAGE));
        cartDao.save(new Cart(memberId, productDao.findProductById(productId), 1));
        assertThat(cartDao.findCartsByMemberId(memberId)).hasSize(1);
    }

    @DisplayName("카트 id로 조회한다.")
    @Test
    void findCartsById() {
        final Long memberId = memberDao.save(MemberFixture.createMember(EMAIL, PASSWORD, NAME));
        final Long productId = productDao.save(createProduct(PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_IMAGE));
        final Long cartId = cartDao.save(new Cart(memberId, productDao.findProductById(productId), 1));

        assertThat(cartDao.findCartById(cartId)).usingRecursiveComparison()
                .isEqualTo(new Cart(cartId, memberId, productDao.findProductById(productId), 1));
    }

    @DisplayName("여러 카트를 조회한다.")
    @Test
    void findCartsByIds() {
        final Long memberId = memberDao.save(MemberFixture.createMember(EMAIL, PASSWORD, NAME));
        final Long productId = productDao.save(createProduct(PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_IMAGE));
        final Long productId2 = productDao.save(createProduct("초콜릿", 1000, "choco"));

        final Long cartId = cartDao.save(new Cart(memberId, productDao.findProductById(productId), 1));
        final Long cartId2 = cartDao.save(new Cart(memberId, productDao.findProductById(productId2), 1));

        assertThat(cartDao.findCartsByIds(List.of(cartId, cartId2))).hasSize(2);
    }

    @DisplayName("카트를 삭제한다.")
    @Test
    void delete() {
        final Long memberId = memberDao.save(MemberFixture.createMember(EMAIL, PASSWORD, NAME));
        final Long productId = productDao.save(createProduct(PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_IMAGE));
        final Long cartId = cartDao.save(new Cart(memberId, productDao.findProductById(productId), 1));
        cartDao.delete(cartId);

        assertThat(cartDao.findCartsByMemberId(memberId)).isEmpty();
    }

    @DisplayName("카트의 물품 개수를 업데이트한다.")
    @Test
    void updateQuantity() {
        final Long memberId = memberDao.save(MemberFixture.createMember(EMAIL, PASSWORD, NAME));
        final Long productId = productDao.save(createProduct(PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_IMAGE));
        final Long cartId = cartDao.save(new Cart(memberId, productDao.findProductById(productId), 1));

        final Cart foundCart = cartDao.findCartById(cartId);
        foundCart.updateQuantity(10);
        cartDao.updateQuantity(foundCart);
        assertThat(foundCart.getQuantity()).isEqualTo(10);
    }

    @DisplayName("여러 장바구니 물품을 삭제한다.")
    @Test
    void deleteBatch() {
        final Long memberId = memberDao.save(MemberFixture.createMember(EMAIL, PASSWORD, NAME));
        final Long productId = productDao.save(createProduct(PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_IMAGE));
        final Long cartId = cartDao.save(new Cart(memberId, productDao.findProductById(productId), 1));
        final Long cartId2 = cartDao.save(new Cart(memberId, productDao.findProductById(productId), 2));
        final Long cartId3 = cartDao.save(new Cart(memberId, productDao.findProductById(productId), 3));

        cartDao.deleteBatch(List.of(cartId, cartId2, cartId3));
        assertThat(cartDao.findCartsByMemberId(memberId)).hasSize(0);
    }
}

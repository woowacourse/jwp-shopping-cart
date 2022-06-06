package woowacourse.shoppingcart.dao;


import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.helper.fixture.MemberFixture.EMAIL;
import static woowacourse.helper.fixture.MemberFixture.NAME;
import static woowacourse.helper.fixture.MemberFixture.PASSWORD;
import static woowacourse.helper.fixture.ProductFixture.PRODUCT_IMAGE;
import static woowacourse.helper.fixture.ProductFixture.PRODUCT_NAME;
import static woowacourse.helper.fixture.ProductFixture.PRODUCT_PRICE;
import static woowacourse.helper.fixture.ProductFixture.createProduct;

import javax.print.attribute.standard.MediaSize.NA;
import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import woowacourse.helper.fixture.MemberFixture;
import woowacourse.helper.fixture.ProductFixture;
import woowacourse.member.dao.MemberDao;
import woowacourse.shoppingcart.domain.Carts;
import woowacourse.shoppingcart.domain.Product;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CartsDaoTest {

    private CartsDao cartsDao;

    private MemberDao memberDao;

    private ProductDao productDao;

    public CartsDaoTest(final DataSource dataSource) {
        cartsDao = new CartsDao(dataSource);
        memberDao = new MemberDao(dataSource);
        productDao = new ProductDao(new JdbcTemplate(dataSource));
    }

    @DisplayName("카트를 저장한다.")
    @Test
    void createCarts() {
        final Long memberId = memberDao.save(MemberFixture.createMember(EMAIL, PASSWORD, NAME));
        final Long productId = productDao.save(createProduct(PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_IMAGE));
        cartsDao.save(new Carts(memberId, productDao.findProductById(productId), 1));
        assertThat(cartsDao.findCartsByMemberId(memberId)).hasSize(1);
    }
}

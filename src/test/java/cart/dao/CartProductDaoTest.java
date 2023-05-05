package cart.dao;

import cart.domain.CartProduct;
import cart.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static cart.fixture.ProductFixture.*;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NonAsciiCharacters")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@JdbcTest
public class CartProductDaoTest {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private CartProductDao cartProductDao;
    private MemberDao memberDao;
    private ProductDao productDao;

    @BeforeEach
    void setUp() {
        namedParameterJdbcTemplate.getJdbcTemplate().execute("ALTER TABLE cart_product ALTER COLUMN id RESTART WITH 1");
        namedParameterJdbcTemplate.getJdbcTemplate().execute("ALTER TABLE product ALTER COLUMN id RESTART WITH 1");
        namedParameterJdbcTemplate.getJdbcTemplate().execute("ALTER TABLE member ALTER COLUMN id RESTART WITH 1");
        cartProductDao = new CartProductDao(namedParameterJdbcTemplate);
        memberDao = new MemberDao(namedParameterJdbcTemplate);
        productDao = new ProductDao(namedParameterJdbcTemplate);
    }

    @Test
    void 모든_장바구니_상품을_조회한다() {
        saveMember();
        saveProducts();

        final CartProduct firstCartProduct = new CartProduct(1L, 1L);
        final CartProduct secondCartProduct = new CartProduct(1L, 2L);

        cartProductDao.save(firstCartProduct);
        cartProductDao.save(secondCartProduct);

        assertThat(cartProductDao.findAllProductByMemberId(1L)).containsExactly(FIRST_PRODUCT_WITH_ID, SECOND_PRODUCT_WITH_ID);
    }

    private void saveMember() {
        final Member member = new Member(1L, "hongo@wooteco.com", "password");
        memberDao.save(member);
    }

    private void saveProducts() {
        System.out.println("save! " + productDao.save(FIRST_PRODUCT));
        System.out.println("save! " + productDao.save(SECOND_PRODUCT));
    }
}

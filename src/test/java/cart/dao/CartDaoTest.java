package cart.dao;

import static cart.fixture.CartFixtures.INSERT_CART_ENTITY;
import static cart.fixture.MemberFixtures.INSERT_MEMBER_ENTITY;
import static cart.fixture.ProductFixtures.INSERT_PRODUCT_ENTITY;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class CartDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private CartDao cartDao;
    private MemberDao memberDao;
    private ProductDao productDao;

    @BeforeEach
    void setUp() {
        this.cartDao = new CartDao(jdbcTemplate);
        this.memberDao = new MemberDao(jdbcTemplate);
        this.productDao = new ProductDao(jdbcTemplate);
    }

    @Test
    @DisplayName("장바구니에 상품을 추가한다.")
    void insert() {
        // given
        memberDao.insert(INSERT_MEMBER_ENTITY);
        productDao.insert(INSERT_PRODUCT_ENTITY);

        // when, then
        assertDoesNotThrow(() -> cartDao.insert(INSERT_CART_ENTITY));
    }
}

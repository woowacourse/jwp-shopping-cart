package cart.cart.dao;

import cart.member.dao.MemberDao;
import cart.member.dao.MemberMemoryDao;
import cart.member.domain.Member;
import cart.product.dao.ProductDao;
import cart.product.dao.ProductMemoryDao;
import cart.product.domain.Product;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static cart.constant.TestConstant.*;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@JdbcTest
class CartMemoryDaoTest {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private CartDao cartDao;
    
    @BeforeEach
    void setUp() {
        cartDao = new CartMemoryDao(namedParameterJdbcTemplate);
        ProductDao productDao = new ProductMemoryDao(namedParameterJdbcTemplate);
        MemberDao memberDao = new MemberMemoryDao(namedParameterJdbcTemplate);
        
        // given
        productDao.save(new Product("product1", "a.com", 1000));
        productDao.save(new Product("product2", "b.com", 2000));
        memberDao.save(new Member("a@a.com", "password1"));
        memberDao.save(new Member("b@b.com", "password2"));
    }
    
    @Test
    void productId와_memberId를_전달하면_장바구니를_저장한다() {
        // when
        final Long cartId = cartDao.save(1L, 1L);
        
        // then
        assertThat(cartId).isOne();
    }
    
    @AfterEach
    void tearDown() {
        final JdbcTemplate jdbcTemplate = namedParameterJdbcTemplate.getJdbcTemplate();
        jdbcTemplate.execute(CART_ID_INIT_SQL);
        jdbcTemplate.execute(PRODUCT_ID_INIT_SQL);
        jdbcTemplate.execute(MEMBER_ID_INIT_SQL);
    }
}

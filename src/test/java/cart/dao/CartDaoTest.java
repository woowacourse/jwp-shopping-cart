package cart.dao;

import cart.domain.Cart;
import cart.domain.Member;
import cart.domain.Product;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DisplayName("CartDao 은")
class CartDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private MemberDao memberDao;
    private ProductDao productDao;
    private CartDao cartDao;

    @BeforeEach
    void setUp() {
        memberDao = new MemberDao(jdbcTemplate);
        productDao = new ProductDao(jdbcTemplate);
        cartDao = new CartDao(jdbcTemplate);
    }

    @Test
    void 카트_저장() {
        // when
        final Long memberId = 맴버_저장();
        final Long productId = 상품_저장();
        final Long id = cartDao.save(new Cart(memberId, productId));

        // then
        assertThat(id).isNotNull();
    }

    @Test
    void 맴버아이디로_카트_리스트() {
        // when
        final Long memberId1 = 맴버_저장();
        final Long productId1 = 상품_저장();
        cartDao.save(new Cart(memberId1, productId1));

        assertThat(cartDao.findByMemberId(memberId1)).hasSize(1);
    }

    @Test
    void 카트_삭제() {
        // when
        final Long memberId1 = 맴버_저장();
        final Long productId1 = 상품_저장();
        Long cartId = cartDao.save(new Cart(memberId1, productId1));
        cartDao.deleteById(cartId);

        assertThat(cartDao.findByMemberId(memberId1)).isEmpty();
    }

    private Long 상품_저장() {
        return productDao.save(new Product("채채", "https://cc", 2000));
    }

    private Long 맴버_저장() {
        return memberDao.save(new Member("aaa@email.com", "aaaa"));
    }
}

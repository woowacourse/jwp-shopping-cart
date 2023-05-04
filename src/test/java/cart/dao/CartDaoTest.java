package cart.dao;

import cart.dao.entity.CartEntity;
import cart.dao.entity.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
class CartDaoTest {

    private CartDao cartDao;
    private MemberDao memberDao;
    private ProductDao productDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        cartDao = new CartDao(jdbcTemplate);
        memberDao = new MemberDao(jdbcTemplate);
        productDao = new ProductDao(jdbcTemplate);
    }

    @Test
    void 카트에_상품을_추가한다() {
        // given
        final Long memberId = memberDao.findIdByAuthInfo("a@a.com", "password1");
        final ProductEntity productEntity = new ProductEntity.Builder()
                .name("치킨")
                .price(10000)
                .image("치킨 이미지")
                .build();
        final Long productId = productDao.insert(productEntity);

        final CartEntity cartEntity = new CartEntity.Builder()
                .memberId(memberId)
                .productId(productId)
                .build();

        // when
        Long cartId = cartDao.insert(cartEntity);

        // then
        assertThat(cartId).isNotNull();

    }

    @Test
    void 카트_상품을_삭제한다() {
        // given
        final Long memberId = memberDao.findIdByAuthInfo("b@b.com", "password2");
        final ProductEntity productEntity = new ProductEntity.Builder()
                .name("치킨")
                .price(10000)
                .image("치킨 이미지")
                .build();
        final Long productId = productDao.insert(productEntity);

        final CartEntity cartEntity = new CartEntity.Builder()
                .memberId(memberId)
                .productId(productId)
                .build();

        // when
        cartDao.insert(cartEntity);
        cartDao.deleteProduct(cartEntity);

        // then
        assertThat(cartDao.findProductsByMemberId(memberId).size())
                .isEqualTo(0);
    }
}
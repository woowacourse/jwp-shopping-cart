package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import cart.domain.CartProduct;
import cart.domain.Member;
import cart.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DisplayName("CartProductDao 는")
@JdbcTest
class CartProductDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private MemberDao memberDao;
    private ProductDao productDao;
    private CartProductDao cartProductDao;

    @BeforeEach
    void setUp() {
        memberDao = new MemberDao(jdbcTemplate);
        productDao = new ProductDao(jdbcTemplate);
        cartProductDao = new CartProductDao(jdbcTemplate);
    }

    @Test
    void 장바구니_상품을_저장한다() {
        // given
        final Long 회원_ID = 회원을_저장한다();
        final Long 상품_ID = 상품을_저장한다();
        final CartProduct cartProduct = new CartProduct(회원_ID, 상품_ID);

        // when
        장바구니_상품을_저장한다(cartProduct);

        // then
        assertThat(cartProductDao.findAllByMemberId(회원_ID)).hasSize(1);
    }

    @Test
    void 같은_회원이_같은_상품을_2개이상_저장하면_오류() {
        // given
        final Long 회원_ID = 회원을_저장한다();
        final Long 상품_ID = 상품을_저장한다();
        final CartProduct cartProduct1 = new CartProduct(회원_ID, 상품_ID);
        final CartProduct cartProduct2 = new CartProduct(회원_ID, 상품_ID);
        장바구니_상품을_저장한다(cartProduct1);

        // when & then
        assertThatThrownBy(() ->
                장바구니_상품을_저장한다(cartProduct2)
        ).isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    void 회원_id로_장바구니_상품을_조회한다() {
        // given
        final Long 회원_ID = 회원을_저장한다();
        final Long 상품1_ID = 상품을_저장한다();
        final Long 상품2_ID = 상품을_저장한다();
        final CartProduct cartProduct1 = new CartProduct(회원_ID, 상품1_ID);
        final CartProduct cartProduct2 = new CartProduct(회원_ID, 상품2_ID);
        장바구니_상품을_저장한다(cartProduct1);
        장바구니_상품을_저장한다(cartProduct2);

        // expected
        assertThat(cartProductDao.findAllByMemberId(회원_ID)).hasSize(2);
    }

    @Test
    void 상품을_제거한다() {
        // given
        final Long 회원_ID = 회원을_저장한다();
        final Long 상품_ID = 상품을_저장한다();
        final CartProduct cartProduct = new CartProduct(회원_ID, 상품_ID);
        final Long 장바구니_상품_ID = 장바구니_상품을_저장한다(cartProduct);

        // when
        cartProductDao.deleteById(장바구니_상품_ID);

        // then
        assertThat(cartProductDao.findAllByMemberId(회원_ID)).isEmpty();
    }

    private Long 상품을_저장한다() {
        return productDao.save(new Product("상품", "https://cc.com", 1000));
    }

    private Long 회원을_저장한다() {
        return memberDao.save(new Member("mallang@email.com", "234"));
    }

    private Long 장바구니_상품을_저장한다(final CartProduct cartProduct) {
        return cartProductDao.save(cartProduct);
    }
}

package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import cart.domain.cart.CartProduct;
import cart.domain.member.Member;
import cart.domain.product.Product;
import cart.fixture.MemberFixture;
import cart.fixture.ProductFixture.BLACKCAT;
import cart.fixture.ProductFixture.HERB;
import java.util.List;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
@JdbcTest
class CartProductJdbcDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private CartProductJdbcDao cartProductDao;
    private ProductJdbcDao productJdbcDao;

    @BeforeEach
    void setUp() {
        cartProductDao = new CartProductJdbcDao(jdbcTemplate);
        productJdbcDao = new ProductJdbcDao(jdbcTemplate);
    }

    @Test
    void 장바구니에_상품을_추가한다() {
        // given
        final Member member = MemberFixture.BLACKCAT.MEMBER;
        final Long productId = insertProduct(BLACKCAT.PRODUCT);
        final CartProduct cartProduct = new CartProduct(member.getId(), productId);

        // when
        final Long id = cartProductDao.saveAndGetId(cartProduct);

        // then
        final List<Product> result = cartProductDao.findAllProductByMember(member);
        assertThat(result).hasSize(1);
    }

    private Long insertProduct(final Product product) {
        return productJdbcDao.saveAndGetId(product).get();
    }

    @Test
    void 특정_사용자의_장바구니에_있는_모든_상품을_반환한다() {
        // given
        final Member member = MemberFixture.BLACKCAT.MEMBER;
        final Long herbProductId = insertProduct(HERB.PRODUCT);
        final Long blackcatProudctId = insertProduct(BLACKCAT.PRODUCT);
        cartProductDao.saveAndGetId(new CartProduct(member.getId(), herbProductId));
        cartProductDao.saveAndGetId(new CartProduct(member.getId(), blackcatProudctId));

        // when
        final List<Product> result = cartProductDao.findAllProductByMember(member);

        // then
        AssertionsForClassTypes.assertThat(result).usingRecursiveComparison().isEqualTo(List.of(
                new Product(
                        herbProductId,
                        HERB.PRODUCT.getName(),
                        HERB.PRODUCT.getImage(),
                        HERB.PRODUCT.getPrice()
                ),
                new Product(
                        blackcatProudctId,
                        BLACKCAT.PRODUCT.getName(),
                        BLACKCAT.PRODUCT.getImage(),
                        BLACKCAT.PRODUCT.getPrice()
                )
        ));
    }

    @Test
    void 사용자와_상품_아이디를_받아_장바구니에서_제거한다() {
        // given
        final Member member = MemberFixture.BLACKCAT.MEMBER;
        final Long productId = insertProduct(HERB.PRODUCT);
        cartProductDao.saveAndGetId(new CartProduct(member.getId(), productId));

        // when
        final int result = cartProductDao.delete(productId, member);

        // then
        assertThat(cartProductDao.findAllProductByMember(member)).isEmpty();
    }
}

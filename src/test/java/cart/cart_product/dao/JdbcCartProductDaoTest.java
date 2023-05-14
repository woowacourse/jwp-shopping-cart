package cart.cart_product.dao;

import cart.DaoTest;
import cart.product.domain.Product;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class JdbcCartProductDaoTest extends DaoTest {

    @Test
    void 장바구니_등록이_되는지_확인한다() {
        final Long memberId = 1L;
        final Long productId = 1L;

        assertDoesNotThrow(() -> cartProductDao.insert(memberId, productId));
    }

    @Test
    void 특정_사용자_id에_대한_상품_id_목록을_확인한다() {
        final Long memberId = 1L;
        final List<Product> productEntities = cartProductDao.findAllProductByCartId(memberId);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(productEntities.size()).isEqualTo(1);
            softAssertions.assertThat(productEntities.get(0).getId()).isEqualTo(2L);
        });
    }

    @Test
    void 사용자_id와_상품_id를_통해_장바구니를_삭제할_수_있는지_확인한다() {
        final Long memberId = 1L;
        final Long productId = 2L;

        assertDoesNotThrow(() -> cartProductDao.deleteByCartIdAndProductId(memberId, productId));
    }
}

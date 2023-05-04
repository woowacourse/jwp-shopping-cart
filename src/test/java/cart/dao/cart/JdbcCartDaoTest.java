package cart.dao.cart;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@Import(JdbcCartDao.class)
@Sql("/reset-cart_product-data.sql")
@JdbcTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class JdbcCartDaoTest {

    @Autowired
    JdbcCartDao cartDao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    void 장바구니_등록이_되는지_확인한다() {
        final Long memberId = 1L;
        final Long productId = 1L;

        assertDoesNotThrow(() -> cartDao.insert(memberId, productId));
    }

    @Test
    void 특정_사용자_id에_대한_상품_id_목록을_확인한다() {
        final Long memberId = 1L;
        final List<Long> productEntities = cartDao.findAllProductIdByMemberId(memberId);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(productEntities.size()).isEqualTo(1);
            softAssertions.assertThat(productEntities.get(0)).isEqualTo(2L);
        });
    }

    @Test
    void 사용자_id와_상품_id를_통해_장바구니를_삭제할_수_있는지_확인한다() {
        final Long memberId = 1L;
        final Long productId = 2L;

        assertDoesNotThrow(() -> cartDao.deleteByMemberIdAndProductId(memberId, productId));
    }
}

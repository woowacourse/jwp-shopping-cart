package cart.cart.dao;

import cart.DaoTest;
import cart.cart.domain.Cart;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class JdbcCartDaoTest extends DaoTest {

    @Test
    void 특정_유저_id에_대한_장바구니용_id를_가져온다() {
        final Long memberId = 1L;
        final Cart cart = cartDao.findById(memberId);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(cart.getId()).isEqualTo(1);
            softAssertions.assertThat(cart.getMemberId()).isEqualTo(1);
        });
    }
}

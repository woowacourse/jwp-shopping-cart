package cart.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import cart.fixture.MemberFixture.BLACKCAT;
import cart.fixture.ProductFixture.HERB;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
@Transactional
@SpringBootTest
class CartProductServiceTest {

    @Autowired
    private CartProductService cartProductService;

    @Test
    void 장바구니에_없는_상품_삭제_요청_시_예외를_발생한다() {
        // expect
        assertThatThrownBy(() -> cartProductService.delete(HERB.PRODUCT.getId(), BLACKCAT.MEMBER))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 상품은 장바구니에 존재하지 않습니다.");
    }
}

package cart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class ProductTest {

    @Test
    void 상품이_정상_생성된다() {
        assertDoesNotThrow(() -> new Product("치킨", 1_000, "치킨사진"));
    }

    @Test
    void 상품_이름이_50자를_넘기면_예외를_던진다() {
        // given
        final String nameOver50 = "치킨".repeat(50);

        // expect
        assertThatThrownBy(() -> new Product(nameOver50, 10000, "치킨사진"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품 이름은 50자를 넘길 수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 1_000_000_001})
    void 가격이_0미만_10억초과이면_예외를_던진다(final int price) {
        // expect
        assertThatThrownBy(() -> new Product("치킨", price, "치킨사진"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("가격은 0 미만이거나, 1000000000 초과일 수 없습니다.");
    }

    @Test
    void 이미지_주소가_2_000자_초과하면_예외를_던진다() {
        // given
        final String imageOver2000 = "치킨사진".repeat(1_001);

        // expect
        assertThatThrownBy(() -> new Product("치킨", 10000, imageOver2000))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미지 주소는 2000자를 넘길 수 없습니다.");
    }
}
package cart.domain;

import cart.dao.entity.ProductEntity;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class ProductEntityTest {

    @Test
    void 상품이_정상_생성된다() {
        assertDoesNotThrow(() -> new ProductEntity.Builder()
                .name("치킨")
                .price(1_000)
                .image("치킨사진")
                .build()
        );
    }

    @Test
    void 상품_이름이_50자를_넘기면_예외를_던진다() {
        // given
        final String nameOver50 = "치킨".repeat(50);

        // expect
        assertThatThrownBy(() -> new ProductEntity.Builder()
                .name(nameOver50)
                .price(10_000)
                .image("치킨사진")
                .build()
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품 이름은 50자를 넘길 수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 1_000_000_001})
    void 가격이_0미만_10억초과이면_예외를_던진다(final int price) {
        // expect
        assertThatThrownBy(() -> new ProductEntity.Builder()
                .name("치킨")
                .price(price)
                .image("치킨사진")
                .build()
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("가격은 0 미만이거나, 1000000000 초과일 수 없습니다.");
    }

    @Test
    void 이미지_주소가_2_000자_초과하면_예외를_던진다() {
        // given
        final String imageOver2000 = "치킨사진".repeat(1_001);

        // expect
        assertThatThrownBy(() -> new ProductEntity.Builder()
                .name("치킨")
                .price(10_000)
                .image(imageOver2000)
                .build()
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미지 주소는 2000자를 넘길 수 없습니다.");
    }
}

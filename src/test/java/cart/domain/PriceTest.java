package cart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("Price 은(는)")
class PriceTest {

    @Test
    void 가격이_0원보다_작으면_오류() {
        // when & then
        assertThatThrownBy(
                () -> Price.price(-1)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 가격이_0원_이상이면_생성된다() {
        // when & then
        assertDoesNotThrow(
                () -> Price.price(1000)
        );
    }
}

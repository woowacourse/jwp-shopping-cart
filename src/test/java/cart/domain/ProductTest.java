package cart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cart.TestFixture;

public class ProductTest {

    @DisplayName("이름이 20글자를 초과하면 예외를 던진다")
    @Test
    void nameLengthOverTwentyThrows() {
        assertThatThrownBy(() -> new Product(
                "namelengthover-twenty",
                TestFixture.IMAGE_0CHIL,
                1000L
        ))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("가격이 1,000,000원을 초과하면 예외를 던진다")
    @Test
    void priceOverAHundredThousandThrows() {
        assertThatThrownBy(() -> new Product(
                "땡칠",
                TestFixture.IMAGE_0CHIL,
                1_000_001L
        ))
                .isInstanceOf(IllegalArgumentException.class);
    }
}

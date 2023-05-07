package cart.domain;

import static cart.TestFixture.IMAGE_CHICKEN;
import static cart.TestFixture.NAME_CHICKEN;
import static cart.TestFixture.PRICE_CHICKEN;
import static cart.TestFixture.PRODUCT_CHICKEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProductTest {

    @DisplayName("이름이 20글자를 초과하면 예외를 던진다")
    @Test
    void nameLengthOverTwentyThrows() {
        assertThatThrownBy(() -> new Product(
                "namelengthover-twenty",
                IMAGE_CHICKEN,
                PRICE_CHICKEN
        ))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("가격이 1,000,000원을 초과하면 예외를 던진다")
    @Test
    void priceOverAHundredThousandThrows() {
        assertThatThrownBy(() -> new Product(
                NAME_CHICKEN,
                IMAGE_CHICKEN,
                1_000_001L
        ))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("이름, 이미지, 가격이 같으면 같다.")
    @Test
    void equals_whenNameImageAndPriceEquals() {
        Product otherProductWithSameProperties = new Product(NAME_CHICKEN, IMAGE_CHICKEN, PRICE_CHICKEN);

        assertThat(PRODUCT_CHICKEN).isEqualTo(otherProductWithSameProperties);
        assertThat(otherProductWithSameProperties).isEqualTo(PRODUCT_CHICKEN);
    }
}

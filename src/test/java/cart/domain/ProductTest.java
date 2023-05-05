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

    @DisplayName("둘 다 id가 없다면 인스턴스를 비교한다")
    @Test
    void compareEqualityWithInstance_whenBothIdsAreNull() {
        Product product = PRODUCT_CHICKEN;

        assertThat(product).isEqualTo(product);
    }

    @DisplayName("한 쪽만 id가 없다면 동등하지 않다")
    @Test
    void notEqual_whenEitherIdIsNull() {
        Product productWithoutId = PRODUCT_CHICKEN;
        Product productWithId = new Product(1, NAME_CHICKEN, IMAGE_CHICKEN, PRICE_CHICKEN);

        assertThat(productWithoutId).isNotEqualTo(productWithId);
        assertThat(productWithId).isNotEqualTo(productWithoutId);
    }

    @DisplayName("둘 다 id가 있고, id가 같으면 같다")
    @Test
    void equals_whenBothHasSameId() {
        Product productWithIdTwo = new Product(2, NAME_CHICKEN, IMAGE_CHICKEN, PRICE_CHICKEN);
        Product otherProductWithIdTwo = new Product(2, NAME_CHICKEN, IMAGE_CHICKEN, PRICE_CHICKEN);

        assertThat(productWithIdTwo).isEqualTo(otherProductWithIdTwo);
        assertThat(otherProductWithIdTwo).isEqualTo(productWithIdTwo);
    }

    @DisplayName("둘 다 id가 있어도, id가 다르면 다르다")
    @Test
    void notEqual_whenBothHasDifferentId() {
        Product productWithIdTwo = new Product(2, NAME_CHICKEN, IMAGE_CHICKEN, PRICE_CHICKEN);
        Product productWithIdOne = new Product(1, NAME_CHICKEN, IMAGE_CHICKEN, PRICE_CHICKEN);

        assertThat(productWithIdTwo).isNotEqualTo(productWithIdOne);
        assertThat(productWithIdOne).isNotEqualTo(productWithIdTwo);
    }
}

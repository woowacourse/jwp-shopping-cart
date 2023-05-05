package cart.domain;

import static org.assertj.core.api.Assertions.assertThat;
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

    @DisplayName("둘 다 id가 없다면 인스턴스를 비교한다")
    @Test
    void compareEqualityWithInstance_whenBothIdsAreNull() {
        Product product = new Product(null, "치킨", "chicken.png", 23000L);

        assertThat(product).isEqualTo(product);
    }

    @DisplayName("한 쪽만 id가 없다면 동등하지 않다")
    @Test
    void notEqual_whenEitherIdIsNull() {
        Product productWithoutId = new Product(null, "치킨", "chicken.png", 23000L);
        Product productWithId = new Product(1, "치킨", "chicken.png", 23000L);

        assertThat(productWithoutId).isNotEqualTo(productWithId);
        assertThat(productWithId).isNotEqualTo(productWithoutId);
    }

    @DisplayName("둘 다 id가 있고, id가 같으면 같다")
    @Test
    void equals_whenBothHasSameId() {
        Product productWithIdTwo = new Product(2, "치킨", "chicken.png", 23000L);
        Product otherProductWithIdTwo = new Product(2, "치킨", "chicken.png", 23000L);

        assertThat(productWithIdTwo).isEqualTo(otherProductWithIdTwo);
        assertThat(otherProductWithIdTwo).isEqualTo(productWithIdTwo);
    }

    @DisplayName("둘 다 id가 있어도, id가 다르면 다르다")
    @Test
    void notEqual_whenBothHasDifferentId() {
        Product productWithIdTwo = new Product(2, "치킨", "chicken.png", 23000L);
        Product productWithIdOne = new Product(1, "치킨", "chicken.png", 23000L);

        assertThat(productWithIdTwo).isNotEqualTo(productWithIdOne);
        assertThat(productWithIdOne).isNotEqualTo(productWithIdTwo);
    }
}

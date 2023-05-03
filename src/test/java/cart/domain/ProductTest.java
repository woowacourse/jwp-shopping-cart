package cart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("Product 은(는)")
class ProductTest {

    @Test
    void 값을_업데이트_할_수_있다() {
        // given
        final Product product = new Product(1L, "상품1", "https://mallang.img", 1000);

        // when
        final Product update = product.update("업데이트 이름", "https://update.img", 2000);

        // then
        assertAll(
                () -> assertThat(update.getId()).isEqualTo(1L),
                () -> assertThat(update.getName()).isEqualTo("업데이트 이름"),
                () -> assertThat(update.getImageUrl()).isEqualTo("https://update.img"),
                () -> assertThat(update.getPrice()).isEqualTo(2000)
        );
    }
}

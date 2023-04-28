package cart.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ProductTest {

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    @DisplayName("of() : 물품의 이름을 공백으로 입력할 시 IllegalArgumentException가 발생한다.")
    void test_of_IllegalArgumentException_name(final String name) throws Exception {
        //given
        final int price = 10000;
        final String imageUrl = "imageUrl";

        //when & then
        Assertions.assertThatThrownBy(() -> Product.of(name, price, imageUrl))
                  .isInstanceOf(IllegalArgumentException.class)
                  .hasMessage("이름은 빈칸이 될 수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    @DisplayName("of() : 물품의 가격을 음수나 0으로 입력할 시 IllegalArgumentException가 발생한다.")
    void test_of_IllegalArgumentException_price(final int price) throws Exception {
        //given
        final String name = "피자";
        final String imageUrl = "imageUrl";

        //when & then
        Assertions.assertThatThrownBy(() -> Product.of(name, price, imageUrl))
                  .isInstanceOf(IllegalArgumentException.class)
                  .hasMessage("가격은 양수만 가능합니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    @DisplayName("of() : 물품의 이미지 url을 공백으로 입력할 시 IllegalArgumentException가 발생한다.")
    void test_of_IllegalArgumentException_image(final String imageUrl) throws Exception {
        //given
        final String name = "피자";
        final int price = 10000;

        //when & then
        Assertions.assertThatThrownBy(() -> Product.of(name, price, imageUrl))
                  .isInstanceOf(IllegalArgumentException.class)
                  .hasMessage("url은 공백이 될 수 없습니다.");
    }
}

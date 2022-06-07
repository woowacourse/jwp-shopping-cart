package woowacourse.shoppingcart.domain.product.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class ImageUrlTest {

    @Test
    void url_생성() {
        ImageUrl imageUrl = new ImageUrl("https://yeonyeon.tistory.com");
        assertThat(imageUrl).isNotNull();
    }

    @Test
    void 글자수_초과한_url_생성() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new ImageUrl("y".repeat(2001)))
                .withMessageContaining("이미지 url 은 2000자가 넘을 수 없습니다.");
    }

    @ParameterizedTest(name = "url : {0}")
    @ValueSource(strings = {" https://yeonyeon.tistory.com", "https://yeonyeon.tistory.com ",
            " https://yeonyeon.tistory.com "})
    void 앞뒤_공백_제거_확인(String value) {
        ImageUrl imageUrl = new ImageUrl(value);
        ImageUrl expected = new ImageUrl("https://yeonyeon.tistory.com");
        assertThat(imageUrl).isEqualTo(expected);
    }
}
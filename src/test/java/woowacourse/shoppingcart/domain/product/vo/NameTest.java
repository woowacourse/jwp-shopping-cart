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
class NameTest {

    @Test
    void 이름_생성() {
        Name name = new Name("연로그");
        assertThat(name).isNotNull();
    }

    @Test
    void 최소_글자수_미만으로_이름_생성() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Name(""))
                .withMessageContaining("상품의 이름은 1자 ~ 50자만 가능합니다.");
    }

    @Test
    void 최대_글자수_초과로_이름_생성() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Name("y".repeat(51)))
                .withMessageContaining("상품의 이름은 1자 ~ 50자만 가능합니다.");
    }

    @ParameterizedTest(name = "상품명 : {0}")
    @ValueSource(strings = {" 연로그", "연로그 ", " 연로그 "})
    void 앞뒤_공백_제거_확인(String value) {
        Name name = new Name(value);
        Name expected = new Name("연로그");
        assertThat(name).isEqualTo(expected);
    }
}

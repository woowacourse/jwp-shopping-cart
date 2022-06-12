package woowacourse.shoppingcart.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.exception.InvalidInformationException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.in;

public class ProductTest {

    @Test
    void 상품_이름이_null인_경우() {
        assertThatThrownBy(() -> new Product(1L, null, 1, "https://www.naver.com"))
                .isInstanceOf(InvalidInformationException.class)
                .hasMessage("[ERROR] 상품 이름은 빈 값일 수 없습니다.");
    }

    @Test
    void 상품_이름이_빈칸인_경우() {
        assertThatThrownBy(() -> new Product(1L, "", 1, "https://www.naver.com"))
                .isInstanceOf(InvalidInformationException.class)
                .hasMessage("[ERROR] 상품 이름은 빈 값일 수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {" abc", "abc "})
    void 상품_이름이_공백으로_시작하거나_끝나는_경우(String invalidName) {
        assertThatThrownBy(() -> new Product(1L, invalidName, 1, "https://www.naver.com"))
                .isInstanceOf(InvalidInformationException.class)
                .hasMessage("[ERROR] 상품 이름은 공백으로 시작하거나 끝날 수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"맛있는_치킨", "맛!있는치킨", "_맛있는 치킨", "맛있는 치킨."})
    void 상품_이름에_특수문저가_들어가는_경우(String invalidName) {
        assertThatThrownBy(() -> new Product(1L, invalidName, 1, "https://www.naver.com"))
                .isInstanceOf(InvalidInformationException.class)
                .hasMessage("[ERROR] 상품 이름에는 특수문자가 들어갈 수 없습니다.");
    }

    @Test
    void 상품_이름이_32자를_초과하는_경우() {
        String invalidName = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        assertThatThrownBy(() -> new Product(1L, invalidName, 1, "https://www.naver.com"))
                .isInstanceOf(InvalidInformationException.class)
                .hasMessage("[ERROR] 상품 이름은 최대 32자 이하여야 합니다.");
    }
}

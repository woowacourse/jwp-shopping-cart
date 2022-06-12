package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import woowacourse.common.exception.InvalidRequestException;

@SuppressWarnings("NonAsciiCharacters")
class ProductTest {

    private static final Long 식별자 = 1L;
    private static final String 유효한_상품명 = "상품명";
    private static final int 유효한_가격 = 1000;
    private static final String 이미지_주소 = "URL";

    @DisplayName("유효성 검증")
    @Nested
    class InitTest {

        @Test
        void 상품명이_공백만으로_구성된_경우_예외발생() {
            String 공백_이름 = "  ";
            assertThatThrownBy(() -> new Product(식별자, 공백_이름, 유효한_가격, 이미지_주소))
                    .isInstanceOf(InvalidRequestException.class);
        }

        @Test
        void 상품명이_255글자를_초과한_경우_예외발생() {
            String 너무_긴_이름 ="일".repeat(256);
            assertThatThrownBy(() -> new Product(식별자, 너무_긴_이름, 유효한_가격, 이미지_주소))
                    .isInstanceOf(InvalidRequestException.class);
        }

        @Test
        void 가격이_음수인_경우_예외발생() {
            int 음수_가격 = -1;
            assertThatThrownBy(() -> new Product(식별자, 유효한_상품명, 음수_가격, 이미지_주소))
                    .isInstanceOf(InvalidRequestException.class);
        }
    }
}

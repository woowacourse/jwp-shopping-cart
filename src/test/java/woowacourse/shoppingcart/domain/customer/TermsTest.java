package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import woowacourse.auth.exception.BadRequestException;

@DisplayName("Terms 는")
public class TermsTest {

    @DisplayName("약관동의 여부를 받았을 떄")
    @Nested
    class TermsValidationTest {

        @Test
        @DisplayName("약관에 동의 했다면 저장한다.")
        void validTerms() {
            assertThatNoException().isThrownBy(() -> new Terms(true));
        }

        @Test
        @DisplayName("약관에 동의하지 않았다면 예외를 던진다.")
        void invalidTerms() {
            assertThatThrownBy(() -> new Terms(false))
                    .isInstanceOf(BadRequestException.class)
                    .hasMessage(Terms.DISAGREED_TERMS);
        }
    }
}

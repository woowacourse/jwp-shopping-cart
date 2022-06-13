package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import woowacourse.auth.exception.BadRequestException;

@DisplayName("BirthDay 는")
class BirthdayTest {

    @DisplayName("생년월일을 입력했을 때")
    @Nested
    class BirthdayValidationTest {

        @Test
        @DisplayName("생년월일이 유효하면 저장한다.")
        void validBirthday() {
            assertThatNoException().isThrownBy(() -> new Birthday("1998-08-07"));
        }

        @Test
        @DisplayName("생년월일이 유효하지 않으면 에러를 던진다.")
        void invalidBirthday() {
            assertThatThrownBy(() -> new Birthday("1998-31-07"))
                    .isInstanceOf(BadRequestException.class)
                    .hasMessage(Birthday.INVALID_BIRTHDAY_FORMAT);
        }
    }
}

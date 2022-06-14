package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import woowacourse.auth.exception.BadRequestException;

@DisplayName("Contact 는")
public class ContactTest {

    @DisplayName("연락처를 입력받았을 때")
    @Nested
    class ContactValidationTest {

        @Test
        @DisplayName("유효한 값이 들어오면 저장한다.")
        void validContact() {
            assertThatNoException().isThrownBy(() -> new Contact("01012346789"));
        }

        @Test
        @DisplayName("유효하지 않은 값이 들어오면 에러를 발생시킨다.")
        void invalidContact() {
            assertThatThrownBy(() -> new Contact("1234567890"))
                    .isInstanceOf(BadRequestException.class)
                    .hasMessage(Contact.INVALID_CONTACT_FORMAT);
        }
    }

}

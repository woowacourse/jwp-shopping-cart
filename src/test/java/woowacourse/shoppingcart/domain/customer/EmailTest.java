package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import woowacourse.auth.exception.BadRequestException;

@DisplayName("Email 은")
public class EmailTest {

    @DisplayName("이메일 주소가 입력되었을 때")
    @Nested
    class EmailFormTest {

        @Test
        @DisplayName("이메일 주소가 올바르면 이메일 주소를을 저장한다.")
        void validEmailForm() {
            assertThatNoException().isThrownBy(() -> new Email("her0807@naver.com"));
        }

        @Test
        @DisplayName("이메일 주소가 올바르지 않으면 예외를 던진다.")
        void invalidEmailForm() {
            assertThatThrownBy(() -> new Email("her0807naver.com"))
                    .isInstanceOf(BadRequestException.class)
                    .hasMessage(Email.INVALID_EMAIL_FORMAT);
        }
    }

}

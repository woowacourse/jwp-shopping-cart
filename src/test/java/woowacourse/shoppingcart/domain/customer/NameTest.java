package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import woowacourse.auth.exception.BadRequestException;

@DisplayName("Name 은")
public class NameTest {

    @DisplayName("이름 생성시")
    @Nested
    class NameValidationTest {

        @DisplayName("이름이 유효하면 저장한다.")
        @Test
        void validName() {
            assertThatNoException().isThrownBy(() -> new Name("스컬수달"));
        }

        @DisplayName("이름이 유효하지 않으면 예외가 발생한다.")
        @Test
        void invalidName() {
            assertThatThrownBy(() -> new Name("스컬수달토미"))
                    .isInstanceOf(BadRequestException.class)
                    .hasMessage(Name.INVALID_NAME_FORMAT);
        }
    }
}

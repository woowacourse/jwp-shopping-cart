package woowacourse.shoppingcart.domain.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.exception.badrequest.InvalidEmailException;

public class EmailTest {

    @DisplayName("이메일 양식이 아닌 경우 예외를 발생시킨다.")
    @Test
    void invalidFormException() {
        Assertions.assertThatThrownBy(() -> new Email("email"))
                .isInstanceOf(InvalidEmailException.class);
    }
}

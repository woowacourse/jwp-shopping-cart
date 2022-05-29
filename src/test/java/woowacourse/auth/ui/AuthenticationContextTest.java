package woowacourse.auth.ui;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.auth.exception.InvalidAuthException;

class AuthenticationContextTest {

    @Test
    @DisplayName("principal이 없는데 get할 경우 예외 발생")
    void getNullPrinciapl_throwException() {
        assertThatThrownBy(() -> new AuthenticationContext().getPrincipal())
                .isInstanceOf(InvalidAuthException.class)
                .hasMessage("principal은 null값이 반환될 수 없습니다.");
    }
}

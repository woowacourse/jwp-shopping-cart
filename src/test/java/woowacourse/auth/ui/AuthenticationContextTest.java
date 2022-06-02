package woowacourse.auth.ui;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AuthenticationContextTest {

    @Test
    @DisplayName("principal이 없는데 get할 경우 예외 발생")
    void getNullPrinciapl_throwException() {
        assertThatThrownBy(() -> new AuthenticationContext().getPrincipal())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("principal은 null값이 반환될 수 없습니다.");
    }

    @Test
    @DisplayName("principal이 있는데 set할 경우 예외 발생")
    void setExistPrincipal_throwException() {
        AuthenticationContext authenticationContext = new AuthenticationContext();
        authenticationContext.setPrincipal("principal");

        assertThatThrownBy(() -> authenticationContext.setPrincipal("principal"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("이미 principal 정보가 있어 수정할 수 없습니다.");
    }
}

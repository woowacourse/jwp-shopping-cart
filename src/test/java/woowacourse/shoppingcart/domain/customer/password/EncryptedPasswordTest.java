package woowacourse.shoppingcart.domain.customer.password;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.exception.datanotmatch.CustomerDataNotMatchException;
import woowacourse.shoppingcart.exception.datanotmatch.LoginDataNotMatchException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("EncryptedPassword 도메인 테스트")
class EncryptedPasswordTest {

    @DisplayName("비밀번호와 일치하지 않을 경우 예외가 발생한다.")
    @Test
    void validateMatchingLoginPassword() {
        // given
        Password password = new EncryptedPassword("1338ad00357397e37ec3990310efd04f767ab485fa8e69f2d06df186f9327372");

        // when & then
        assertThatThrownBy(() -> password.validateMatchingLoginPassword("invalidPassword"))
                .isInstanceOf(LoginDataNotMatchException.class)
                .hasMessage("비밀번호가 일치하지 않습니다.");
    }

    @DisplayName("비밀번호와 일치하지 않을 경우 예외가 발생한다.")
    @Test
    void validateMatchingOriginalPassword() {
        // given
        Password password = new EncryptedPassword("1338ad00357397e37ec3990310efd04f767ab485fa8e69f2d06df186f9327372");

        // when & then
        assertThatThrownBy(() -> password.validateMatchingOriginalPassword("invalidPassword"))
                .isInstanceOf(CustomerDataNotMatchException.class)
                .hasMessage("기존 비밀번호와 입력한 비밀번호가 일치하지 않습니다.");
    }
}

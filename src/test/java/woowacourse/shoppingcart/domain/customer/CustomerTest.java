package woowacourse.shoppingcart.domain.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.exception.dataempty.CustomerDataEmptyException;
import woowacourse.shoppingcart.exception.dataformat.CustomerDataFormatException;
import woowacourse.shoppingcart.exception.datanotmatch.CustomerDataNotMatchException;
import woowacourse.shoppingcart.exception.datanotmatch.LoginDataNotMatchException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Customer 도메인 테스트")
class CustomerTest {

    @DisplayName("아이디에 null 을 입력하면 예외가 발생한다.")
    @Test
    void userIdNullException() {
        // when & then
        assertThatThrownBy(() -> Customer.from(null, null, "유콩", "1234asdf!"))
                .isInstanceOf(CustomerDataEmptyException.class)
                .hasMessage("아이디를 입력해주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("아이디에 빈값을 입력하면 예외가 발생한다.")
    void userIdBlankException(String userId) {
        // when & then
        assertThatThrownBy(() -> Customer.from(null, userId, "유콩", "1234asdf!"))
                .isInstanceOf(CustomerDataEmptyException.class)
                .hasMessage("아이디를 입력해주세요.");
    }

    @DisplayName("닉네임에 null 을 입력하면 예외가 발생한다.")
    @Test
    void nicknameNullException() {
        // when & then
        assertThatThrownBy(() -> Customer.from(null, "userId@woowacourse.com", null, "1234asdf!"))
                .isInstanceOf(CustomerDataEmptyException.class)
                .hasMessage("닉네임을 입력해주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("닉네임에 빈값을 입력하면 예외가 발생한다.")
    void nicknameBlankException(String nickname) {
        // when & then
        assertThatThrownBy(() -> Customer.from(null, "userId@woowacourse.com", nickname, "1234asdf!"))
                .isInstanceOf(CustomerDataEmptyException.class)
                .hasMessage("닉네임을 입력해주세요.");
    }

    @DisplayName("비밀번호에 null 을 입력하면 예외가 발생한다.")
    @Test
    void passwordNullException() {
        // when & then
        assertThatThrownBy(() -> Customer.from(null, "userId@woowacourse.com", "유콩", null))
                .isInstanceOf(CustomerDataEmptyException.class)
                .hasMessage("비밀번호를 입력해주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("비밀번호에 빈값을 입력하면 예외가 발생한다.")
    void passwordBlankException(String password) {
        // when & then
        assertThatThrownBy(() -> Customer.from(null, "userId@woowacourse.com", "유콩", password))
                .isInstanceOf(CustomerDataEmptyException.class)
                .hasMessage("비밀번호를 입력해주세요.");
    }

    @DisplayName("아이디가 이메일 형식이 아니면 예외가 발생한다.")
    @Test
    void userIdFormatException() {
        // when & then
        assertThatThrownBy(() -> Customer.from(null, "userId", "유콩", "1234asdf!"))
                .isInstanceOf(CustomerDataFormatException.class)
                .hasMessage("아이디는 이메일 형식으로 입력해주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "aaaaaaaaaaa", "!@#$"})
    @DisplayName("닉네임이 영문, 한글, 숫자를 조합하여 2 ~ 10 자가 아니면 예외가 발생한다.")
    void nicknameFormatException(String nickname) {
        // when & then
        assertThatThrownBy(() -> Customer.from(null, "userId@woowacourse.com", nickname, "1234asdf!"))
                .isInstanceOf(CustomerDataFormatException.class)
                .hasMessage("닉네임은 영문, 한글, 숫자를 조합하여 2 ~ 10 자를 입력해주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345678!", "asdsad^f#$", "1231234ads", "asd2$$", "adsfsdaf324234#@$#@$#@"})
    @DisplayName("비밀번호가 영문, 한글, 숫자를 필수로 조합한 8 ~ 16 자가 아니면 예외가 발생한다.")
    void passwordFormatException(String password) {
        // when & then
        assertThatThrownBy(() -> Customer.from(null, "userId@woowacourse.com", "유콩", password))
                .isInstanceOf(CustomerDataFormatException.class)
                .hasMessage("비밀번호는 영문, 특수문자, 숫자를 필수로 조합하여 8 ~ 16 자를 입력해주세요.");
    }

    @DisplayName("로그인 시 비밀번호가 일치하지 않으면 예외가 발생한다.")
    @Test
    void validateMatchingLoginPassword() {
        // given
        Customer customer = Customer.from(null, "userId@woowacourse.com", "유콩", "1234asdf!");

        // when & then
        assertThatThrownBy(() -> customer.validateMatchingLoginPassword("invalidPassword"))
                .isInstanceOf(LoginDataNotMatchException.class)
                .hasMessage("비밀번호가 일치하지 않습니다.");
    }

    @DisplayName("내 정보 조회 시 입력한 비밀번호가 기존 비밀번호와 일치하지 않으면 예외가 발생한다.")
    @Test
    void validateMatchingOriginalPassword() {
        // given
        Customer customer = Customer.from(null, "userId@woowacourse.com", "유콩", "1234asdf!");

        // when & then
        assertThatThrownBy(() -> customer.validateMatchingOriginalPassword("invalidPassword"))
                .isInstanceOf(CustomerDataNotMatchException.class)
                .hasMessage("기존 비밀번호와 입력한 비밀번호가 일치하지 않습니다.");
    }
}

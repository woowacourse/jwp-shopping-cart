package woowacourse.shoppingcart.unit.customer.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.customer.domain.Nickname;
import woowacourse.shoppingcart.customer.exception.badrequest.InvalidNicknameException;

class NicknameTest {

    @ParameterizedTest
    @DisplayName("닉네임이 유효하지 않으면 예외를 던진다.")
    @ValueSource(strings = {"", "  ", "  a  ", "^^", "1 2 3 4 5 6 7 8", "123456789"})
    void newNickname_invalidValue_exceptionThrown(final String value) {
        // when, then
        assertThatThrownBy(() -> new Nickname(value))
                .isInstanceOf(InvalidNicknameException.class);
    }

    @ParameterizedTest
    @DisplayName("닉네임이 유효하면 객체를 생성한다.")
    @ValueSource(strings = {"ab", "릭릭", "a1", "12345678", "abcdefgh", "가나다라마바사아"})
    void newNickname_validValue_objectCreated(final String value) {
        // when
        final Nickname nickname = new Nickname(value);
        final String actual = nickname.getValue();

        // then
        assertThat(actual).isEqualTo(value);
    }
}
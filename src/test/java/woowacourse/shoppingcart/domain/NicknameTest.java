package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.domain.customer.Nickname;
import woowacourse.shoppingcart.exception.bodyexception.ValidateException;

public class NicknameTest {

    @ParameterizedTest
    @ValueSource(strings = {"k", "valueqwer1", "", "    "})
    @DisplayName("닉네임 형식이 잘못된 경우 예외를 발생시킨다.")
    void new_wrongForm_exceptionThrown(String value) {
        // when, then
        assertThatThrownBy(() -> new Nickname(value))
                .isInstanceOf(ValidateException.class)
                .hasMessage("닉네임 양식이 잘못 되었습니다.");
    }
}

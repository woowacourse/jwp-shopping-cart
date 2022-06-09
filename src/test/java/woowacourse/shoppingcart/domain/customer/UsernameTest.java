package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.exception.InputFormatException;

public class UsernameTest {

    @ParameterizedTest
    @DisplayName("닉네임이 규약에 맞으면 정상적으로 생성된다.")
    @ValueSource(strings = {"aki", "1234567891", "AkiIsKing1"})
    void InputRightUsername(String value) {
        //then
        assertThatCode(() -> Username.of(value))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @DisplayName("닉네임이 1~10글자 사이가 아니라면 에러를 발생한다.")
    @ValueSource(strings = {"AkiIsKing11", "", " "})
    void InputWrongLengthUsername(String value) {
        //then
        assertThatCode(() -> Username.of(value))
                .isInstanceOf(InputFormatException.class);
    }
}

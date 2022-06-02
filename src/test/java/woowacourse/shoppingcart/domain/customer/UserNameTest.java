package woowacourse.shoppingcart.domain.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.exception.InvalidArgumentRequestException;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class UserNameTest {

    @DisplayName("아이디가 공백이면 예외가 발생한다")
    @Test
    void construct_blank() {
        assertThatExceptionOfType(InvalidArgumentRequestException.class)
                .isThrownBy(() -> new UserName(" "))
                .withMessageContaining("공백");
    }

    @DisplayName("비밀번호가 4글자 미만이거나 20글자 초과이면 예외가 발생한다")
    @ParameterizedTest(name = "{0}")
    @ValueSource(strings = {"abc", "forkyforkyforky1234*!"})
    void construct_length(String userName) {
        assertThatExceptionOfType(InvalidArgumentRequestException.class)
                .isThrownBy(() -> new UserName(userName))
                .withMessageContaining("4자 이상 20자 이하");
    }
}
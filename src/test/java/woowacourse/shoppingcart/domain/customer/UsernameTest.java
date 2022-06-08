package woowacourse.shoppingcart.domain.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import woowacourse.shoppingcart.exception.UsernameValidationException;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class UsernameTest {

    static Stream<String> blankNames() {
        return Stream.of("   ", null);
    }

    @ParameterizedTest
    @MethodSource("blankNames")
    @DisplayName("닉네임이 공백이거나 null인 경우 예외를 반환한다.")
    void Username_Blank(String blankNames) {
        assertThatThrownBy(() -> new Username(blankNames))
                .isInstanceOf(UsernameValidationException.class)
                .hasMessage("닉네임에는 공백이 들어가면 안됩니다.");
    }

    @Test
    @DisplayName("닉네임이 규약보다 긴 경우 예외를 반환한다.")
    void Username_Length() {
        assertThatThrownBy(() -> new Username("tooLongUsername"))
                .isInstanceOf(UsernameValidationException.class)
                .hasMessage("닉네임은 1자 이상 10자 이하여야합니다.");
    }

}

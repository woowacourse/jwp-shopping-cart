package woowacourse.shoppingcart.domain.vo;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.exception.UsernameValidationException;

class UsernameTest {

    @Test
    @DisplayName("값이 비어있는 객체를 생성한다.")
    void empty() {
        final Username empty = Username.empty();

        assertThat(empty.getValue()).isNull();
    }

    @ParameterizedTest
    @ValueSource(strings = {"aaaaaaaaaaa"})
    @DisplayName("닉네임의 길이가 1미만 10초과일 경우 예외가 발생한다.")
    void createWithInvalidLength(final String rawUsername) {
        assertThatThrownBy(() -> new Username(rawUsername))
                .isInstanceOf(UsernameValidationException.class)
                .hasMessageContaining("닉네임은 1자 이상 10자 이하여야합니다.");
    }
}
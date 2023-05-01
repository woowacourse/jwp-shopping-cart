package cart.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("상품 이름은")
class NameTest {

    @DisplayName("1 ~ 10자 이내가 아니면 예외 처리된다.")
    @ParameterizedTest(name = "케이스: {0}")
    @ValueSource(strings = {"", "abcdefghijk"})
    void validateNameLengthTest(String input) {
        assertThatThrownBy(() -> new Name(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 이름의 길이는 1~10자만 가능합니다.");
    }
}

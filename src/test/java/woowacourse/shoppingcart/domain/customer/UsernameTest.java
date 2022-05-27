package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class UsernameTest {

    @ParameterizedTest
    @ValueSource(strings = {"no", "abcdefghijklmnop"})
    @DisplayName("이름이 3자 미만 15자 초과 시 예외 발생")
    void invalidNameLength_throwException(String username) {
        assertThatThrownBy(() -> new Username(username))
                .isInstanceOf(IllegalArgumentException.class);
    }

}

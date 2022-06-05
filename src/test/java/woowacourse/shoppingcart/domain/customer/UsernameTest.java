package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.domain.customer.values.Username;

class UsernameTest {

    @ParameterizedTest
    @ValueSource(strings = {"no", "abcdefghijklmnop"})
    @DisplayName("이름이 3자 미만 15자 초과 시 예외 발생")
    void invalidNameLength_throwException(String username) {
        assertThatThrownBy(() -> new Username(username))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"한글은안됨", "only_english"})
    @DisplayName("이름은 영어와 숫자만 허용")
    void invalidNamePattern_throwException(String username) {
        assertThatThrownBy(() -> new Username(username))
                .isInstanceOf(IllegalArgumentException.class);
    }
}

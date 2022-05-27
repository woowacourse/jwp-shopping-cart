package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class PasswordTest {

    @ParameterizedTest
    @ValueSource(strings = {"abcdefg", "abcdefghijklmnopqrstu"})
    @DisplayName("비밀번호가 8자 미만 20자 초과 시 예외 발생")
    void invalidPasswordLength_throwException(String password) {
        assertThatThrownBy(() -> new Password(password))
                .isInstanceOf(IllegalArgumentException.class);
    }
}

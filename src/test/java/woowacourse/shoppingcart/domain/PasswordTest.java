package woowacourse.shoppingcart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class PasswordTest {

    @ParameterizedTest
    @CsvSource({"1234567,false", "12345678,false", "1a2b3C4d!,true", "12345678123456789,false",
            "1_2_3_47a,false", " a,false", "1a2b3C4d1a2b3C4d,false", "1a2b3C4d1a2b3C4!,true"})
    @DisplayName("유저 비밀번호를 테스트한다.")
    void validateTest(String password, boolean result) {
        if (result) {
            assertDoesNotThrow(() -> {
                new Password(password);
            });
            return;
        }
        assertThatThrownBy(() -> {
            new Password(password);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}

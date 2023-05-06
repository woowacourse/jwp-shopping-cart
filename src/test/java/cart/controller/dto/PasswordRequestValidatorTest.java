package cart.controller.dto;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PasswordRequestValidatorTest {

    @DisplayName("문자, 숫자, 특수문자를 모두 포함한 경우 예외가 발생하지 않는다.")
    @Test
    void shouldNotThrowExceptionWhenContainsAlphabetAndNumberAndSpecialCharacter() {
        assertDoesNotThrow(() -> PasswordRequestValidator.validate("1237192873291ASDF7421a!@#$%^&*()"));
    }
}

package cart.entity.vo;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmailTest {


    @ParameterizedTest(name = "이메일 형식에 맞지 않으면 예외 반환 테스트 - 입력값 : {0}")
    @CsvSource(value = {"NULL", "EMPTY", "wrongEmail", "wrong@email"}, nullValues = "NULL", emptyValue = "EMPTY")
    void email_validation_test(final String providedEmail) {
        assertThatThrownBy(() -> new Email(providedEmail))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("email이 형식에 맞지 않습니다. 입력된 값 : " + providedEmail);
    }
}

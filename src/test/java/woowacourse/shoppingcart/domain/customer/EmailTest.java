package woowacourse.shoppingcart.domain.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import woowacourse.shoppingcart.exception.EmailValidationException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class EmailTest {

    @ParameterizedTest
    @CsvSource(value = {"@t.com", "tooLongToMakeEmailtooLongToMakeEmailtooLongToMakeEmail@checkLengthValidation.com"})
    @DisplayName("이메일의 길이규약을 검증하고, 조건에 맞지않으면 예외를 반환한다.")
    void Email(String testEmail) {
        assertThatThrownBy(() -> new Email(testEmail))
                .isInstanceOf(EmailValidationException.class)
                .hasMessage("이메일은 8자 이상 50자 이하여야합니다.");
    }

}

package cart.domain.user;

import cart.domain.product.ProductName;
import cart.exception.GlobalException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class EmailTest {
    @ParameterizedTest(name = "이메일 생성 테스트")
    @ValueSource(strings = {"aaa@naver.com", "a@naver.com","zuny1234@woowahan.com"})
    void createEmail(String input) {
        assertDoesNotThrow(() -> Email.from(input));
    }

    @ParameterizedTest(name = "이메일의 길이는 1글자 미만, 30글자를 초과할 수 없다.")
    @ValueSource(strings = {"","0123456789@01234567890123456789"})
    void createProductNameFailureBlank(String input) {
        assertThatThrownBy(() -> Email.from(input))
                .isInstanceOf(GlobalException.class);
    }

    @ParameterizedTest(name = "이메일 형식이 올바르지 않을 경우, 생성할 수 없다.")
    @ValueSource(strings = {"a a a @naver.com", "@naver.com", "asdf"})
    void createProductNameFailureOverMaxLength(String input) {
        assertThatThrownBy(() -> Email.from(input))
                .isInstanceOf(GlobalException.class);
    }
}

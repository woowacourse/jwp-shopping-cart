package woowacourse.shoppingcart.domain.customer.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class AccountTest {

    @ParameterizedTest(name = "계정명 : {0}")
    @ValueSource(strings = {"yhh1056", "yeon06", "eden7777"})
    void 계정_생성(String value) {
        Account account = new Account(value);
        assertThat(account).isNotNull();
    }

    @ParameterizedTest(name = "계정명 : {0}")
    @ValueSource(strings = {"yhh1056__", "YEON06", "에덴7777"})
    void 소문자와_숫자가_아닌_계정_생성(String value) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Account(value))
                .withMessage(String.format("계정은 소문자와 숫자로 생성 가능합니다. 입력값: %s", value));
    }

    @ParameterizedTest(name = "계정명 : {0}")
    @ValueSource(strings = {"yhh", "1234567890123456"})
    void 올바르지_않은_글자수_계정_생성(String value) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Account(value))
                .withMessage(String.format("계정은 4 ~ 15자로 생성 가능합니다. 입력값: %s", value));
    }

    @ParameterizedTest(name = "계정명 : {0}")
    @ValueSource(strings = {" yeon06 ", "yeon06 ", " yeon06"})
    void 앞뒤_공백_제거_후_생성(String value) {
        Account account = new Account(value);
        Account expectedAccount = new Account("yeon06");

        assertThat(account).isEqualTo(expectedAccount);
    }
}

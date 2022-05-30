package woowacourse.auth.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.DisplayName;
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

    @DisplayName("계정은 소문자와 숫자만 가능")
    @ParameterizedTest(name = "계정명 : {0}")
    @ValueSource(strings = {"yhh1056__", "YEON06", "에덴7777"})
    void 계정_생성_실패(String value) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Account(value))
                .withMessage(String.format("계정은 소문자와 숫자로 생성 가능합니다. 입력값: %s", value));
    }
}

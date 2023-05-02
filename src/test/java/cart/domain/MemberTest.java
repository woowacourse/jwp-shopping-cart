package cart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberTest {

    @ParameterizedTest
    @ValueSource(strings = {"손흥민닷컴", "코일", "example"})
    void 이메일이_형식과_맞지_않으면_예외가_발생한다(final String nonEmailString) {
        // expect
        assertThatThrownBy(() -> new Member(nonEmailString, "qwerty1234"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이메일 형식과 일치하지 않습니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"한글로된비밀번호", "onlyEnglish", "123456"})
    void 비밀번호가_형식과_맞지_않으면_예외가_발생한다(final String wrongPassword) {
        // expect
        assertThatThrownBy(() -> new Member("a@b.com", wrongPassword))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호는 영어와 숫자를 포함해야 합니다.");
    }
}

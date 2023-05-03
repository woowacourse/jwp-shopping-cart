package cart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberTest {

    @Test
    void 회원이_정상_생성된다() {
        assertDoesNotThrow(() -> new Member("aa@aa.com","qwerty1234"));
    }


    @ParameterizedTest
    @ValueSource(strings = {"손흥민닷컴", "코일", "example"})
    void 이메일이_형식과_맞지_않으면_예외가_발생한다(final String nonEmail) {
        // expect
        assertThatThrownBy(() -> new Member(nonEmail, "qwerty1234"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이메일 형식과 일치하지 않습니다.");
    }

    @Test
    void 비밀번호의_길이는_4_이상이어야_한다() {
        // given
        final String shortPassword = "일이삼";
        // expect
        assertThatThrownBy(() -> new Member("a@b.com", shortPassword))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호의 길이는 4 이상이어야 합니다.");
    }
}

package woowacourse.study;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
class RegexTest {

    private static final String REGEX = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^*])(?!.*[\\s]).+$";
    private static final Pattern PATTERN = Pattern.compile(REGEX);

    @Test
    void 알파벳_특수문자_숫자_전부_하나_이상씩_포함되면_참() {
        String 유효한_비밀번호 = "a!1";
        Matcher matcher = PATTERN.matcher(유효한_비밀번호);

        boolean matches = matcher.matches();

        assertThat(matches).isTrue();
    }

    @Test
    void 알파벳이_없으면_거짓() {
        String 알파벳_누락 = "123456!@#$%";
        Matcher matcher = PATTERN.matcher(알파벳_누락);

        boolean matches = matcher.matches();

        assertThat(matches).isFalse();
    }

    @Test
    void 특수문자_누락시_거짓() {
        String 특수문자_누락 = "asdf12345";
        Matcher matcher = PATTERN.matcher(특수문자_누락);

        boolean matches = matcher.matches();

        assertThat(matches).isFalse();
    }

    @Test
    void 숫자가_없으면_거짓() {
        String 숫자_누락 = "asdf!@#$%";
        Matcher matcher = PATTERN.matcher(숫자_누락);

        boolean matches = matcher.matches();

        assertThat(matches).isFalse();
    }

    @Test
    void 공백_포함되면_거짓() {
        String 공백_포함 = "asdf !@#$ 12345";
        Matcher matcher = PATTERN.matcher(공백_포함);

        boolean matches = matcher.matches();

        assertThat(matches).isFalse();
    }


    @Test
    void 길이가_8글자에서_20글자인_경우에_대한_검증() {
        String REGEX = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^*])(?!.*[\\s]).{8,20}$";
        Pattern PATTERN = Pattern.compile(REGEX);
        String 글자수_조건_미달 = "asd@123";
        Matcher matcher = PATTERN.matcher(글자수_조건_미달);

        boolean matches = matcher.matches();

        assertThat(matches).isFalse();
    }
}

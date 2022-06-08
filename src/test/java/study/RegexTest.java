package study;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@SuppressWarnings("NonAsciiCharacters")
class RegexTest {

    private static final String 한글자_이상씩_필수_정규식 = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^*])[a-zA-Z0-9!@#$%^*]*$";
    private static final Pattern 한글자_이상씩_필수_패턴 = Pattern.compile(한글자_이상씩_필수_정규식);

    private static final String 알파벳과_숫자_혼합_정규식 = "^[a-zA-Z0-9]{4,20}$";
    private static final Pattern 알파벳과_숫자_혼합_패턴 = Pattern.compile(알파벳과_숫자_혼합_정규식);

    private static final String 한글_허용_정규식 = "^[가-힣a-zA-Z0-9]{1,10}$";
    private static final Pattern 한글_허용_패턴 = Pattern.compile(한글_허용_정규식);

    @Test
    void 알파벳_특수문자_숫자로만_구성되며_종류별로_하나_이상씩_포함되면_참() {
        String 유효한_비밀번호 = "a!1";

        Matcher matcher = 한글자_이상씩_필수_패턴.matcher(유효한_비밀번호);

        boolean matches = matcher.matches();

        assertThat(matches).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"123456!@#$%", "asdf12345", "asdf!@#$%", "asdf !@#$ 12345", "ㅎㅁㅈa!2"})
    void 알파벳_특수문자_숫자_하나_이상씩_조건_거짓(String input) {
        boolean matches = 한글자_이상씩_필수_패턴.matcher(input).matches();

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

    @ParameterizedTest
    @ValueSource(strings = {"asdfg", "123456"})
    void 알파벳과_숫자로_자유롭게_구성된_4글자에서_20글자_사이(String input) {
        boolean matches = 알파벳과_숫자_혼합_패턴.matcher(input).matches();

        assertThat(matches).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"닉네임aA1", "닉네임", "asdfg", "123456"})
    void 한글_알파벳_숫자로_자유롭게_구성된_1글자에서_10글자_사이(String input) {
        boolean matches = 한글_허용_패턴.matcher(input).matches();

        assertThat(matches).isTrue();
    }
}

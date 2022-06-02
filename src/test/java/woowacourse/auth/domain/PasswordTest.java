package woowacourse.auth.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
class PasswordTest {

    @DisplayName("초기화 테스트 - 비밀번호는 알파벳, 숫자, 특수문자로 구성되어야 합니다. (8~20글자)")
    @Nested
    class InitTest {

        @Test
        void 유효한_값으로_인스턴스_생성_가능() {
            String 유효한_비밀번호 = "password!@123";

            assertThatNoException().isThrownBy(() -> new Password(유효한_비밀번호));
        }

        @Test
        void 알파벳이_없으면_예외_발생() {
            String 알파벳_누락 = "123456!@#$%";
            assertThatThrownBy(() -> new Password(알파벳_누락))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 특수문자_누락시_예외_발생() {
            String 특수문자_누락 = "asdf12345";

            assertThatThrownBy(() -> new Password(특수문자_누락))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 숫자가_없으면_예외_발생() {
            String 숫자_누락 = "asdf!@#$%";

            assertThatThrownBy(() -> new Password(숫자_누락))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 공백_포함되면_예외_발생() {
            String 공백_포함 = "asdf !@#$ 12345";

            assertThatThrownBy(() -> new Password(공백_포함))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 길이가_8글자_미만인_경우_예외_발생() {
            String 길이가_7인_비밀번호 = "asd@123";

            assertThatThrownBy(() -> new Password(길이가_7인_비밀번호))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 길이가_20글자_초과인_경우_예외_발생() {
            String 길이가_21인_비밀번호 = "asdfg!@#$%12345678901";

            assertThatThrownBy(() -> new Password(길이가_21인_비밀번호))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 한글이_포함된_경우_예외발생() {
            String 한글_포함_비밀번호 = "ㅁㄴㅇabc!@#12";

            assertThatThrownBy(() -> new Password(한글_포함_비밀번호))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void toEncrypted_메서드는_같은_비밀번호_값에_대해서도_임의의_솔트를_생성하여_암호화_수행() {
        String 유효한_비밀번호 = "password!1";
        Password 비밀번호 = new Password(유효한_비밀번호);
        Password 같은_비밀번호 = new Password(유효한_비밀번호);

        EncryptedPassword actual = 비밀번호.toEncrypted();
        EncryptedPassword expected = 같은_비밀번호.toEncrypted();

        assertThat(actual).isNotEqualTo(expected);
    }
}

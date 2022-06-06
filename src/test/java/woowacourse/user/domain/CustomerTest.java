package woowacourse.user.domain;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import woowacourse.auth.domain.EncryptedPassword;
import woowacourse.common.exception.InvalidRequestException;

@SuppressWarnings("NonAsciiCharacters")
class CustomerTest {

    private static final String 유효한_아이디 = "validname";
    private static final EncryptedPassword 암호화된_비밀번호 = new EncryptedPassword("비밀번호");
    private static final String 유효한_닉네임 = "닉네임";

    @Test
    void 값이_유효한_경우_인스턴스_생성_성공() {
        assertThatNoException().isThrownBy(() -> new Customer(유효한_아이디, 암호화된_비밀번호, 유효한_닉네임, 10));
    }

    @DisplayName("아이디는 알파벳과 숫자로 자유롭게 구성된 4~20글자")
    @Nested
    class UsernameTest {

        @Test
        void 아이디는_숫자만으로도_구성가능() {
            assertThatNoException().isThrownBy(() -> new Customer("123456", 암호화된_비밀번호, 유효한_닉네임, 10));
        }

        @Test
        void 아이디가_4글자_미만인_경우_예외발생() {
            assertThatThrownBy(() -> new Customer("abc", 암호화된_비밀번호, 유효한_닉네임, 10))
                    .isInstanceOf(InvalidRequestException.class);
        }

        @Test
        void 아이디가_20글자_초과인_경우_예외발생() {
            String tooLongUsername = "012345678901234567890";
            assertThatThrownBy(() -> new Customer(tooLongUsername, 암호화된_비밀번호, 유효한_닉네임, 10))
                    .isInstanceOf(InvalidRequestException.class);
        }
    }

    @DisplayName("닉네임은 한글, 알파벳, 숫자로 자유롭게 구성된 1~10글자")
    @Nested
    class NicknameTest {

        @Test
        void 닉네임은_한글만으로도_구성가능() {
            assertThatNoException().isThrownBy(() -> new Customer(유효한_아이디, 암호화된_비밀번호, "닉네임", 10));
        }

        @Test
        void 닉네임이_0글자인_경우_예외발생() {
            assertThatThrownBy(() -> new Customer(유효한_아이디, 암호화된_비밀번호, "", 10))
                    .isInstanceOf(InvalidRequestException.class);
        }

        @Test
        void 닉네임이_10글자_초과인_경우_예외발생() {
            String tooLongNickname = "12345678901";
            assertThatThrownBy(() -> new Customer(유효한_아이디, 암호화된_비밀번호, tooLongNickname, 10))
                    .isInstanceOf(InvalidRequestException.class);
        }
    }

    @DisplayName("나이는 0~200세")
    @Nested
    class AgeTest {

        @Test
        void 나이가_음수인_경우_예외발생() {
            assertThatThrownBy(() -> new Customer(유효한_아이디, 암호화된_비밀번호, 유효한_닉네임, -1))
                    .isInstanceOf(InvalidRequestException.class);
        }

        @Test
        void 나이가_200_초과인_경우_예외발생() {
            assertThatThrownBy(() -> new Customer(유효한_아이디, 암호화된_비밀번호, 유효한_닉네임, 201))
                    .isInstanceOf(InvalidRequestException.class);
        }
    }
}

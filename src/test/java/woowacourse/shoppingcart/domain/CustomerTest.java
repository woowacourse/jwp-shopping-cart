package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
class CustomerTest {

    @DisplayName("초기화 테스트")
    @Nested
    class InitTest {

        @Test
        void 값이_유효한_경우_성공() {
            assertThatNoException()
                    .isThrownBy(() -> new Customer("유효한_아이디", "비밀번호", "닉네임", 10));
        }

        @Test
        void 아이디가_4글자_미만인_경우_예외발생() {
            assertThatThrownBy(() -> new Customer("3글자", "비밀번호", "닉네임", 10))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 아이디가_20글자_초과인_경우_예외발생() {
            String tooLongUsername = "123456789012345678901";
            assertThatThrownBy(() -> new Customer(tooLongUsername, "비밀번호", "닉네임", 10))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 닉네임이_0글자인_경우_예외발생() {
            assertThatThrownBy(() -> new Customer("유효한_아이디", "비밀번호", "", 10))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 닉네임이_10글자_초과인_경우_예외발생() {
            String tooLongNickname = "12345678901";
            assertThatThrownBy(() -> new Customer("유효한_아이디", "비밀번호", tooLongNickname, 10))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 나이가_0이하인_경우_예외발생() {
            assertThatThrownBy(() -> new Customer("유효한_아이디", "비밀번호", "닉네임", 0))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}

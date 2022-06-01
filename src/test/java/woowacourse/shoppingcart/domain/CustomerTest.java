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
            Username username = new Username("유효한_아이디");
            Password password = new Password("비밀번호");
            Nickname nickname = new Nickname("닉네임");
            Age age = new Age(10);
            assertThatNoException()
                    .isThrownBy(() -> new Customer(username, password, nickname, age));
        }
        
        @Test
        void 닉네임이_0글자인_경우_예외발생() {
            Username username = new Username("유효한_아이디");
            Password password = new Password("비밀번호");
            Nickname nickname = new Nickname("");
            Age age = new Age(10);
            assertThatThrownBy(() -> new Customer(username, password, nickname, age))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 닉네임이_10글자_초과인_경우_예외발생() {
            Username username = new Username("유효한_아이디");
            Password password = new Password("비밀번호");
            Nickname nickname = new Nickname("12345678901");
            Age age = new Age(10);
            String tooLongNickname = "12345678901";
            assertThatThrownBy(() -> new Customer(username, password, nickname, age))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 나이가_0이하인_경우_예외발생() {
            Username username = new Username("유효한_아이디");
            Password password = new Password("비밀번호");
            Nickname nickname = new Nickname("닉네임");
            Age age = new Age(0);
            assertThatThrownBy(() -> new Customer(username, password, nickname, age))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}

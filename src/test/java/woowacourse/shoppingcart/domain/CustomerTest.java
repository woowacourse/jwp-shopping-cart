package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static woowacourse.Fixture.페퍼_비밀번호;
import static woowacourse.Fixture.페퍼_아이디;
import static woowacourse.Fixture.페퍼_이름;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CustomerTest {

    @Nested
    @DisplayName("회원을 생성하는 메서드는")
    class createCustomer {

        @Test
        @DisplayName("적합한 아이디, 이름, 비밀번호를 입력하면 회원을 생성한다.")
        void customer() {
            assertThatNoException()
                    .isThrownBy(() -> new Customer(페퍼_아이디, 페퍼_이름, 페퍼_비밀번호));
        }

        @ParameterizedTest
        @ValueSource(strings = {"testwoowacoursecom", "test@woowacoursecom", "testwoowacourse.com", "@", ".", "@.",
                ".@wo.com", "test.woowacourse@com", "", " "})
        @DisplayName("아이디가 형식에 맞지 않을 경우 예외를 던진다.")
        void customer_LoginFail(String loginId) {
            Assertions.assertThatThrownBy(() -> new Customer(loginId, 페퍼_이름, 페퍼_비밀번호))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("아이디 형식이 잘못되었습니다.");
        }

        @ParameterizedTest
        @ValueSource(strings = {"qwer1234!", "QWER1234!", "Qwer1234", "Qwerasdf!", "Qwer12!", "Qwer1234567890!@", "", " "})
        @DisplayName("비밀번호가 형식에 맞지 않을 경우 예외를 던진다")
        void customer_PasswordFail(String password) {
            Assertions.assertThatThrownBy(() -> new Customer(페퍼_아이디, 페퍼_이름, password))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("비밀번호 형식이 잘못되었습니다.");
        }
    }
}

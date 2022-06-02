package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static woowacourse.Fixture.페퍼;
import static woowacourse.Fixture.페퍼_비밀번호;
import static woowacourse.Fixture.페퍼_아이디;
import static woowacourse.Fixture.페퍼_이름;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

@Nested
@DisplayName("Customer 클래스의")
class CustomerTest {

    @Nested
    @DisplayName("생성하는 메서드는")
    class createCustomer {

        @Test
        @DisplayName("적합한 아이디, 이름, 비밀번호를 입력하면 회원을 생성한다.")
        void customer() {
            assertThatNoException()
                    .isThrownBy(() -> new Customer(페퍼_아이디, 페퍼_이름, 페퍼_비밀번호));
        }

        @ParameterizedTest
        @ValueSource(strings = {"testwoowacoursecom", "test@woowacoursecom", "testwoowacourse.com", "@", ".", "@.",
                ".@wo.com", "test.woowacourse@com"})
        @EmptySource
        @DisplayName("아이디가 형식에 맞지 않을 경우 예외를 던진다.")
        void customer_LoginFail(String loginId) {
            assertThatThrownBy(() -> new Customer(loginId, 페퍼_이름, 페퍼_비밀번호))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("아이디 형식이 잘못되었습니다.");
        }
    }

    @Nested
    @DisplayName("isSamePassword 메서드는")
    class isSamePassword {

        @Test
        @DisplayName("비밀번호가 일치하면 참을 반환한다.")
        void isSame() {
            assertThat(페퍼.isSamePassword(페퍼_비밀번호)).isTrue();
        }

        @Test
        @DisplayName("비밀번호가 일치하지 않으면 거짓을 반환한다.")
        void isDifferent() {
            assertThat(페퍼.isSamePassword("asdf1234")).isFalse();
        }
    }

}

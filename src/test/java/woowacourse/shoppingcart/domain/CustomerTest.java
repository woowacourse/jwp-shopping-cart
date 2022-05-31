package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

class CustomerTest {

    @DisplayName("객체 생성 시 이메일이 규칙에 어긋나는 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "잘못된 이메일",
            "이메일@email.com",
            "iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii@gmail.com",
            "emailemail.com",
            "email@emailcom",
            "@email.com",
            "email@email",
            " "
    })
    void createByInvalidEmail(final String invalidEmail) {
        assertThatThrownBy(() -> new Customer(invalidEmail, "쿼리치", "password123!"))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessage("잘못된 이메일 형식입니다.");
    }

    @DisplayName("객체 생성 시 닉네임이 규칙에 어긋나는 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "",
            " ",
            "안 녕",
            "eng",
            "eng1",
            "1234",
            "안녕하십니까"
    })
    void createByInvalidNickname(final String invalidNickname) {
        assertThatThrownBy(() -> new Customer("email@email.com", invalidNickname, "password123!"))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessage("잘못된 닉네임 형식입니다.");
    }

    @DisplayName("객체 생성 시 비밀번호가 규칙에 어긋나는 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "",
            " ",
            "asd123!",
            "abcdefghijklmnop1234!",
            "qerqwerqwad!",
            "asdfasdf123",
            "123123123!",
            "alsdifaencwe",
            "12312312313",
            "!!!!!!!!!!!",
            "password 123",
            "password123{"
    })
    void createByInvalidPassword(final String invalidPassword) {
        assertThatThrownBy(() -> new Customer("email@email.com", "쿼리치", invalidPassword))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessage("잘못된 비밀번호 형식입니다.");
    }
}
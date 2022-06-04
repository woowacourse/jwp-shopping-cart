package woowacourse.shoppingcart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.domain.customer.Customer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomerTest {

    @DisplayName("이메일 형식이 맞지 않는 경우 예외가 발생한다.")
    @Test
    void throwsExceptionWhenInvalidEmailFormat() {
        // given
        String email = "beomWhale";
        String nickname = "범고래";
        String password = "Password12345!";

        // when && then
        assertThatThrownBy(() -> new Customer(email, nickname, password))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이메일 형식이 맞지 않습니다.");
    }

    @DisplayName("패스워드 형식이 맞지 않는 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"password123!", "PASSWORD123!", "Password123", "Password!@#", "Aa1!123",
            "Password123412341234!"})
    void throwsExceptionWhenInvalidPasswordFormat(String password) {
        // given
        String email = "beomWhale@gmail.com";
        String nickname = "범고래";

        // when && then
        assertThatThrownBy(() -> new Customer(email, nickname, password))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("패스워드 형식이 맞지 않습니다.");
    }

    @DisplayName("닉네임 형식이 맞지 않는 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "NICKNAME!", "nickname123"})
    void throwsExceptionWhenInvalidNicknameFormat(String nickname) {
        // given
        String email = "beomWhale@gmail.com";
        String password = "Password12345!";

        // when && then
        assertThatThrownBy(() -> new Customer(email, nickname, password))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("닉네임 형식이 맞지 않습니다.");
    }

    @DisplayName("입력된 패스워드가 기존 패스워드와 일치하는지 확인한다.")
    @Test
    void checkPassword() {
        String password = "Password123!";
        Customer customer = new Customer("awesome@gmail.com", "awesome", password);

        boolean isMatch = customer.isPasswordMatched(password);

        assertThat(isMatch).isTrue();
    }

    @DisplayName("새로운 패스워드를 입력받아 패스워드를 변경한다.")
    @Test
    void changePassword() {
        String password = "Password123!";
        Customer customer = new Customer("awesome@gmail.com", "awesome", password);
        String newPassword = "Password1234!";

        customer.changePassword(password, newPassword);

        assertThat(customer.isPasswordMatched(newPassword)).isTrue();
    }

    @DisplayName("패스워드 변경 시, 이전 패스워드와 다르면 예외가 발생한다.")
    @Test
    void throwExceptionWhenNotMatchPassword() {
        String password = "Password123!";
        Customer customer = new Customer("awesome@gmail.com", "awesome", password);
        String newPassword = "Password1234!";

        assertThatThrownBy(() -> customer.changePassword(password + "1", newPassword))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이전 패스워드가 틀렸습니다.");
    }

    @DisplayName("패스워드 변경 시, 새로운 패스워드가 형식에 맞지 않는 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"password123!", "PASSWORD123!", "Password123", "Password!@#", "Aa1!123"})
    void throwExceptionWhenInvalidPassword(String newPassword) {
        String password = "Password123!";
        Customer customer = new Customer("awesome@gmail.com", "awesome", password);

        assertThatThrownBy(() -> customer.changePassword(password, newPassword))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("패스워드 형식이 맞지 않습니다.");
    }

    @DisplayName("새로운 닉네임을 입력받아 닉네임을 변경한다.")
    @Test
    void changeNickname() {
        // given
        String prevNickname = "awesome";
        Customer customer = new Customer("awesome@gmail.com", prevNickname, "Password123!");

        // when
        String newNickname = "changed";
        customer.changeNickname(newNickname);

        // then
        assertThat(customer.getNickname()).isEqualTo(newNickname);
    }

    @DisplayName("닉네임 변경 시, 새로운 닉네임이 형식에 맞지 않는 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "NICKNAME!", "nickname123"})
    void throwExceptionWhenInvalidNickname(String newNickname) {
        // given
        String prevNickname = "awesome";
        Customer customer = new Customer("awesome@gmail.com", prevNickname, "Password123!");

        // when
        assertThatThrownBy(() -> customer.changeNickname(newNickname))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("닉네임 형식이 맞지 않습니다.");
    }
}

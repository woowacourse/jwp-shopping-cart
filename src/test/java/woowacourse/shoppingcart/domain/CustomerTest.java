package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CustomerTest {

    @Test
    @DisplayName("회원은 이메일, 패스워드, 닉네임으로 생성할 수 있다.")
    void createCustomer() {
        // given
        String email = "email@email.com";
        String password = "password123A@";
        String nickname = "rookie";

        // when
        Customer customer = new Customer(1L, email, password, nickname);

        // then
        assertThat(customer).usingRecursiveComparison()
                .isEqualTo(new Customer(1L, email, password, nickname));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "email@com", "email.com"})
    @DisplayName("이메일은 기본 형식을 지키지 않으면 예외가 발생한다.")
    void checkEmail(String email) {
        // given
        String password = "password123A@";
        String nickname = "rookie";

        // when & then
        assertThatThrownBy(() -> new Customer(1L, email, password, nickname))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "!", "nickname123"})
    @DisplayName("닉네임은 1자 이상 10자 이하, 한글, 영어, 숫자가 아니라면 예외가 발생한다.")
    void checkNickname(String nickname) {
        // given
        String email = "email@mail.com";
        String password = "password123A@";

        // when & then
        assertThatThrownBy(() -> new Customer(1L, email, password, nickname))
                .isInstanceOf(IllegalArgumentException.class);
    }
}

package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
class CustomerTest {

    @DisplayName("비밀번호 같으면 true 반환")
    @Test
    void hasSamePasswordTest() {
        Username username = new Username("유효한_아이디");
        Password password = new Password("password1@");
        Nickname nickname = new Nickname("닉네임");
        Age age = new Age(10);
        Customer customer = new Customer(username, password, nickname, age);

        boolean result = customer.hasSamePassword(password);

        assertThat(result).isTrue();
    }
}

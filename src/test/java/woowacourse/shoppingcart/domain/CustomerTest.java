package woowacourse.shoppingcart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomerTest {

    @Test
    @DisplayName("customer 객체 생성")
    void createCustomer() {
        Customer customer = new Customer("green", "green@woowa.net", "123456q!");

        assertThat(customer).usingRecursiveComparison()
                .isEqualTo(new Customer("green", "green@woowa.net", "123456q!"));
    }

    @Test
    @DisplayName("유저네임이 공백인 경우 예외")
    void usernameNullException() {
        assertThatThrownBy(() -> new Customer("", "green@woowa.net", "123456q!"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("유저 네임과 이메일, 비밀번호를 모두 입력해주세요.");
    }

    @Test
    @DisplayName("유저네임이 32자 초과인 경우 예외")
    void usernameSizeException() {
        String username = IntStream.range(0, 30)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining());

        assertThatThrownBy(() -> new Customer(username, "green@woowa.net", "123456q!"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("유저네임은 32자 이하로 작성해주세요.");
    }

    @Test
    @DisplayName("이메일이 공백인 경우 예외")
    void emailNullException() {
        assertThatThrownBy(() -> new Customer("green", "", "123456q!"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("유저 네임과 이메일, 비밀번호를 모두 입력해주세요.");
    }

    @Test
    @DisplayName("이메일이 64자 초과인 경우 예외")
    void passwordEmailException() {
        String email = IntStream.range(0, 40)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining());

        assertThatThrownBy(() -> new Customer("green", email, "123456q!"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이메일은 64자 이하로 작성해주세요.");
    }

    @Test
    @DisplayName("이메일에 한글이 포함된 경우 예외")
    void emailHasWrongCharacterException() {
        assertThatThrownBy(() -> new Customer("green", "green그린@woowa.net", "123456q!"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이메일에 한글을 입력할 수 없습니다.");
    }

    @ParameterizedTest
    @DisplayName("이메일 형식이 맞지 않는 경우 예외")
    @CsvSource(value = {"@woowa.net", "greenwoowa.net"})
    void emailWrongFormat(String email) {
        assertThatThrownBy(() -> new Customer("green", email, "123456q!"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이메일 형식에 맞춰주세요.");
    }

    @Test
    @DisplayName("비밀번호가 공백인 경우 예외")
    void passwordNullException() {
        assertThatThrownBy(() -> new Customer("green", "green@woowa.net", ""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("유저 네임과 이메일, 비밀번호를 모두 입력해주세요.");
    }

    @Test
    @DisplayName("비밀번호가 6자 미만인 경우 예외")
    void passwordSizeException() {
        String password = IntStream.range(0, 5)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining());

        assertThatThrownBy(() -> new Customer("green", "green@woowa.net", password))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("비밀 번호는 6자 이상으로 작성해주세요.");
    }

    @Test
    @DisplayName("비밀번호에 한글이 포함된 경우 예외")
    void passwordFormatException() {
        assertThatThrownBy(() -> new Customer("green", "green@woowa.net", "그린1234"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("비밀번호에 한글을 입력할 수 없습니다.");
    }
}
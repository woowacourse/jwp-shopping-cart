package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.exception.customer.InvalidEmailException;
import woowacourse.shoppingcart.exception.customer.InvalidNickNameException;

public class CustomerTest {

    private static final String EMAIL = "email@email.com";
    private static final String PASSWORD = "12345678a";
    private static final String NICKNAME = "tonic";

    @DisplayName("비밀번호가 일치 여부 확인")
    @ParameterizedTest
    @CsvSource({"12345678a,true", "12345678b,false"})
    void isValidPassword(String password, boolean expected) {
        Customer customer = new Customer(EMAIL, PASSWORD, NICKNAME);

        assertThat(customer.isValidPassword(password)).isEqualTo(expected);
    }

    @DisplayName("이메일 형식 테스트")
    @ParameterizedTest
    @CsvSource("email@email.,email,email@email")
    void validateEmailTest(String invalidEmail) {

        assertThatThrownBy(() -> new Customer(invalidEmail, PASSWORD, NICKNAME))
                .isInstanceOf(InvalidEmailException.class);
    }

    @DisplayName("닉네임 형식 테스트")
    @ParameterizedTest
    @CsvSource("t,tonictonc")
    void validateNicknameTest(String invalidNickname) {

        assertThatThrownBy(() -> new Customer(EMAIL, PASSWORD, invalidNickname))
                .isInstanceOf(InvalidNickNameException.class);
    }
}

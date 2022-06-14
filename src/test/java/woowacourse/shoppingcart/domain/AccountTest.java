package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AccountTest {

    @DisplayName("비밀번호가 일치 여부 확인")
    @ParameterizedTest
    @CsvSource({"12345678a,true", "12345678b,false"})
    void isValidPassword(String password, boolean expected) {
        Account account = new Account("email@email.com", "12345678a", "tonic");

        assertThat(account.isValidPassword(password)).isEqualTo(expected);
    }
}

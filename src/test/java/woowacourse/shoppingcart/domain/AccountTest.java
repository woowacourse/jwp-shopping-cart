package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import woowacourse.shoppingcart.domain.customer.Account;

class AccountTest {

    @Test
    @DisplayName("아이디를 생성한다.")
    void createAccount() {
        //given
        Account account = new Account("leo0842");
        //when

        //then
        assertThat(account.getValue()).isEqualTo("leo0842");
    }

    @ParameterizedTest
    @CsvSource(value = {"a_a!!", "12345678790123456!!"})
    @DisplayName("특수문자를 제외하고 아이디 길이는 4~15자를 만족해야 한다.")
    void invalidAccountLengthAfterProcessing(String account) {
        //given

        //when

        //then
        assertThatThrownBy(() -> new Account(account))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("아이디는 특수문자를 제외하고 4~15자를 만족해야 합니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {"YEONLOG,yeonlog", "aa_01,aa01"})
    @DisplayName("아이디가 대문자이면 소문자로 변경하고 특수문자가 들어가면 제거한다.")
    void changeAccountPattern(String rawAccount, String processedAccount) {
        //given

        //when
        Account account = new Account(rawAccount);
        //then
        assertThat(account.getValue()).isEqualTo(processedAccount);
    }

    @ParameterizedTest
    @CsvSource(value = {"에덴짱123", "abc에덴동산", "v뽀대에덴v"})
    @DisplayName("회원가입 시 아이디에 한글이 포함되면 예외를 반환한다.")
    void notAllowedKoreanAccount(String account) {
        //given

        //when

        //then
        assertThatThrownBy(() -> new Account(account))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("한글 아이디는 허용되지 않습니다.");
    }

}

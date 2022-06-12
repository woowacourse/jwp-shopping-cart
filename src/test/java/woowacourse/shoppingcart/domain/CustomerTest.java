package woowacourse.shoppingcart.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.exception.InvalidCustomerException;
import woowacourse.shoppingcart.exception.InvalidInformationException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CustomerTest {

    @Test
    void 틀린_비밀번호를_입력하는_경우() {
        String encodedPW = new Password("aaaaaa").generateEncodedPassword();
        Customer customer = new Customer("puterism", "crew01@naver.com", encodedPW);
        Customer other = new Customer("puterism", "crew01@naver.com", "a123456");
        assertThatThrownBy(() -> customer.validateSamePassword(other)).isInstanceOf(InvalidCustomerException.class)
                .hasMessage("[ERROR] 비밀번호가 일치하지 않습니다.");
    }

    @Test
    void 이름이_null인_경우() {
        assertThatThrownBy(() -> new Customer(null, "crew01@naver.com", "a12345")).isInstanceOf(InvalidInformationException.class)
                .hasMessage("[ERROR] 사용자 이름은 빈 값일 수 없습니다.");
    }

    @Test
    void 이름이_빈_칸인_경우() {
        assertThatThrownBy(() -> new Customer("", "crew01@naver.com", "a12345")).isInstanceOf(InvalidInformationException.class)
                .hasMessage("[ERROR] 사용자 이름은 빈 값일 수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {" abc", "a bc", "abc "})
    void 이름에_공백이_들어가는_경우(String invalidName) {
        assertThatThrownBy(() -> new Customer(invalidName,"crew01@naver.com", "a12345")).isInstanceOf(InvalidInformationException.class)
                .hasMessage("[ERROR] 사용자 이름에는 공백이 들어갈 수 없습니다.");
    }

    @Test
    void 이름이_32자_초과인_경우() {
        String invalidName = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        assertThatThrownBy(() -> new Customer(invalidName, "crew01@naver.com", "a12345")).isInstanceOf(InvalidInformationException.class)
                .hasMessage("[ERROR] 사용자 이름은 최대 32자 이하여야 합니다.");
    }
}

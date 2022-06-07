package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import woowacourse.shoppingcart.domain.customer.HashedPassword;
import woowacourse.shoppingcart.domain.customer.RawPassword;

public class HashedPasswordTest {

    @Test
    @DisplayName("해쉬된 문자열로 생성한다.")
    void createHashedPassword() {
        // given
        final String hashedValue = "25D55AD283AA400AF464C76D713C07AD";

        // when, then
        assertThatCode(() -> new HashedPassword(hashedValue))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @DisplayName("평문이 암호화된 비밀번호와 일치하는 지 확인한다.")
    @CsvSource(value = {"12345678,12345678,true", "12345678,87654321,false"})
    void checkPassword(String rawValue, String anotherValue, boolean expect) {
        // given
        final HashedPassword hashedPassword = HashedPassword.from(new RawPassword(rawValue));

        // when
        final boolean actual = hashedPassword.isSameFrom(anotherValue);

        // then
        assertThat(actual).isEqualTo(expect);
    }
}

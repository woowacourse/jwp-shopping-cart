package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.exception.invalid.InvalidPasswordException;

class PlainPasswordTest {

    @ParameterizedTest
    @ValueSource(strings = {"Ellie1234!", "Hae_ri1987@", "%#Hae_Ri12!!"})
    void 비밀번호_생성(String value) {
        // when
        PlainPassword password = new PlainPassword(value);

        // then
        assertThat(password.getValue()).isEqualTo(value);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Ellie1!", "Ellie123456789!!!"})
    void 비밀번호가_8자_미만_16자_초과일_경우_예외_발생(String password) {
        assertThatThrownBy(() -> new PlainPassword(password))
                .isInstanceOf(InvalidPasswordException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Ellie12345", "ellie12345!", "Ellie!!@@"})
    void 비밀번호가_패턴에_맞지_않을_경우_예외_발생(String password) {
        assertThatThrownBy(() -> new PlainPassword(password))
                .isInstanceOf(InvalidPasswordException.class);
    }
}

package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.exception.invalid.InvalidUserNameException;

class UserNameTest {

    @ParameterizedTest
    @ValueSource(strings = {"ellie", "haeri", "jennie"})
    void 이름_생성(String value) {
        // when
        UserName userName = new UserName(value);

        // then
        assertThat(userName.getValue()).isEqualTo(value);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "aaaa", "aaaaaaaaaaaaaaaaaaaaa"})
    void 이름이_5자_미만_20자_초과일_경우_예외_발생(String name) {
        assertThatThrownBy(() -> new UserName(name))
                .isInstanceOf(InvalidUserNameException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Ellie", "Ellie22@", "Elli_$"})
    void 이름이_패턴에_맞지_않을_경우_예외_발생(String name) {
        assertThatThrownBy(() -> new UserName(name))
                .isInstanceOf(InvalidUserNameException.class);
    }
}

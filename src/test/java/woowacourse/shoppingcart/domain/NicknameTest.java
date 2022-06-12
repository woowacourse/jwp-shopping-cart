package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.domain.customer.Nickname;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

class NicknameTest {

    @DisplayName("객체 생성 시 닉네임이 규칙에 어긋나는 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "",
            " ",
            "안 녕",
            "eng",
            "eng1",
            "1234",
            "안녕하십니까"
    })
    void createByInvalidNickname(final String invalidNickname) {
        assertThatThrownBy(() -> new Nickname(invalidNickname))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessage("잘못된 닉네임 형식입니다.");
    }
}

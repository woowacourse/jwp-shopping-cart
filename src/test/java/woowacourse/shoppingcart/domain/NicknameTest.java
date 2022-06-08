package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import woowacourse.shoppingcart.domain.customer.Nickname;

class NicknameTest {

    @Test
    @DisplayName("닉네임을 생성한다.")
    void createNickname() {
        //given
        Nickname nickname = new Nickname("eden");
        //when

        //then
        assertThat(nickname.getValue()).isEqualTo("eden");
    }

    @ParameterizedTest
    @CsvSource(value = {"1", "12345678901"})
    @DisplayName("닉네임의 길이가 2~10자를 벗어나면 예외를 반환한다.")
    void invalidNicknameLength(String nickname) {
        //given

        //when

        //then
        assertThatThrownBy(() -> new Nickname(nickname))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("닉네임 길이는 2~10자를 만족해야 합니다.");
    }
}

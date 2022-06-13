package woowacourse.shoppingcart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NicknameTest {

    @DisplayName("닉네임의 길이가 2~10이면 닉네임을 생성한다.")
    @ParameterizedTest
    @CsvSource(value = {"ni", "nickname12"})
    void makeNickname(String nickname) {
        assertThat(new Nickname(nickname)).isNotNull();
    }

    @DisplayName("닉네임이 비어있으면 예외를 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void throwWhenNicknameNullOrEmpty(String nickname) {
        assertThatThrownBy(() -> new Nickname(nickname))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("닉네임은 비어있을 수 없습니다.");
    }

    @DisplayName("닉네임 길이가 2~10을 만족하지 못하면 예외를 발생한다.")
    @ParameterizedTest
    @CsvSource(value = {"n", "nicknameLong"})
    void throwWhenInvalidNicknameLength(String nickname) {
        assertThatThrownBy(() ->
                new Nickname(nickname))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("닉네임 길이는 2~10자를 만족해야 합니다.");
    }
}

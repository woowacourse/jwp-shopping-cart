package woowacourse.auth.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class NicknameTest {

    @ParameterizedTest
    @ValueSource(strings = {" 호호 ", "호호 ", " 호호"})
    void 앞뒤_공백_제거_후_생성(String value) {
        Nickname nickname = new Nickname(value);
        Nickname expect = new Nickname("호호");

        assertThat(nickname).isEqualTo(expect);
    }
}

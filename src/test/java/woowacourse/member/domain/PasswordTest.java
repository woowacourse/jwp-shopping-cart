package woowacourse.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PasswordTest {

    @DisplayName("비밀번호가 일치한다면 true를 반환한다.")
    @Test
    void equals() {
        Password p1 = new Password("Wooteco123!");
        Password p2 = new Password("Wooteco123!");
        assertThat(p1.equals(p2)).isTrue();
    }

    @DisplayName("비밀번호가 일치하지 않는다면 false를 반환한다.")
    @Test
    void notEquals() {
        Password p1 = new Password("Wooteco123!");
        Password p2 = new Password("!321Wooteco");
        assertThat(p1.equals(p2)).isFalse();
    }
}

package cart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("Member 은(는)")
class MemberTest {

    @Test
    void 로그인_할_수_있다() {
        // given
        final Member member = new Member("mallang@mallang.com", "1234");

        // when & then
        assertDoesNotThrow(
                () -> member.login("1234")
        );
    }

    @Test
    void 로그인에_실패시_예외() {
        // given
        final Member member = new Member("mallang@mallang.com", "1234");

        // when & then
        assertThatThrownBy(
                () -> member.login("12")
        ).isInstanceOf(IllegalArgumentException.class);
    }
}

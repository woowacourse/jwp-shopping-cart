package cart.persistence.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import cart.exception.EntityMappingException;
import cart.persistence.entity.Member;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MemberTest {

    private final String length20Email = "012345678@012345.789";
    private final String length20Password = "01234567890123456789";

    @Test
    void 이메일과_비밀번호_길이_20_성공_테스트() {
        assertDoesNotThrow(() -> new Member(length20Email, length20Password));
    }

    @Test
    void 이메일_길이_21_예외_발생_테스트() {
        final String length21Email = length20Email + "0";

        assertThrows(EntityMappingException.class, () -> new Member(length21Email, length20Password));
    }

    @Test
    void 비밀번호_길이_21_예외_발생_테스트() {
        final String length21Password = length20Password + "0";

        assertThrows(EntityMappingException.class, () -> new Member(length20Email, length21Password));
    }
}

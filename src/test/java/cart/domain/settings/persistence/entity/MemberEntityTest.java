package cart.domain.settings.persistence.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import cart.domain.EntityMappingException;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MemberEntityTest {

    private final String length20Email = "012345678@012345.789";
    private final String length20Password = "01234567890123456789";

    @Test
    void 이메일과_비밀번호_길이_20_성공_테스트() {
        assertDoesNotThrow(() -> new MemberEntity(length20Email, length20Password));
    }

    @Test
    void 이메일_길이_21_예외_발생_테스트() {
        final String length21Email = length20Email + "0";

        assertThrows(EntityMappingException.class, () -> new MemberEntity(length21Email, length20Password));
    }

    @Test
    void 비밀번호_길이_21_예외_발생_테스트() {
        final String length21Password = length20Password + "0";

        assertThrows(EntityMappingException.class, () -> new MemberEntity(length20Email, length21Password));
    }
}

package cart.domain.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MemberTest {

    @Test
    @DisplayName("사용자 생성에 성공한다.")
    void create_member_success() {
        // when & then
        assertDoesNotThrow(() -> Member.from("abc@abc.com", "!@abc123"));
    }
}

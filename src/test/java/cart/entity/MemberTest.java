package cart.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    @DisplayName("id 값을 기준으로 객체가 동일한지 판단한다.")
    @Test
    void equals_true() {
        Member member1 = new Member(1L, "123", "password");
        Member member2 = new Member(1L, "4444", "123");

        assertThat(member1.equals(member2)).isTrue();
    }

    @DisplayName("id 값이 다르다면 객체가 다르다고 판단한다.")
    @Test
    void equals_false() {
        Member member1 = new Member(1L, "123", "password");
        Member member2 = new Member(2L, "123", "password");

        assertThat(member1.equals(member2)).isFalse();
    }
}

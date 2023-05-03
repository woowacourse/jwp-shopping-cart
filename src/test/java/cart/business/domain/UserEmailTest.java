package cart.business.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserEmailTest {

    @Test
    @DisplayName("올바른 이메일 형식이 아니라면 예외를 던진다")
    void test_userEmail_exception() {
        //when, then
        Assertions.assertThrows(IllegalArgumentException.class, () -> new UserEmail("@naver.com"));
    }

    @Test
    @DisplayName("올바른 이메일 형식이 아니라면 잘 생성된다")
    void test_userEmail() {
        //when, then
        Assertions.assertDoesNotThrow(() -> new UserEmail("judith@naver.com"));
    }
}

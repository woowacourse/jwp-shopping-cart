package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class SecurityManagerTest {

    @Test
    void 비밀번호가_일치하는지_확인() {
        String pw = "a12345";
        String encodedPw = SecurityManager.generateEncodedPassword(pw);
        assertThat(SecurityManager.isSamePassword(pw, encodedPw)).isTrue();
    }

    @Test
    void 비밀번호가_일치하지않는_경우() {
        String pw = "a12345";
        String encodedPw = SecurityManager.generateEncodedPassword(pw);
        assertThat(SecurityManager.isSamePassword("a1234", encodedPw)).isFalse();
    }
}

package woowacourse.shoppingcart.domain.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

class UsernamesTest {

    @Test
    @DisplayName("중복되지 않은 회원 아이디 목록을 생성한다.")
    void construct_usernames() {
        final Set<Username> usernames = Set.of(new Username("kth990303"), new Username("forky"));

        assertThatNoException().isThrownBy(() -> new Usernames(usernames));
    }

    @Test
    @DisplayName("회원의 중복 여부를 반환한다.")
    void duplicate_username() {
        final Username username = new Username("abcde");
        final Usernames usernames = new Usernames(Set.of(username));

        assertThat(usernames.contains(username)).isTrue();
    }
}
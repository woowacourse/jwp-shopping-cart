package woowacourse.shoppingcart.domain.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

class UserNamesTest {

    @Test
    @DisplayName("중복되지 않은 회원 아이디 목록을 생성한다.")
    void construct_usernames() {
        final Set<UserName> usernames = Set.of(new UserName("kth990303"), new UserName("forky"));

        assertThatNoException().isThrownBy(() -> new UserNames(usernames));
    }

    @Test
    @DisplayName("회원의 중복 여부를 반환한다.")
    void duplicate_username() {
        final UserName username = new UserName("abcde");
        final UserNames userNames = new UserNames(Set.of(username));

        assertThat(userNames.contains(username)).isTrue();
    }
}
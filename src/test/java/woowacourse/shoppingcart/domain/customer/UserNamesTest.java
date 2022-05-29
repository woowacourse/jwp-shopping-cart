package woowacourse.shoppingcart.domain.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

class UserNamesTest {

    @Test
    @DisplayName("중복되지 않은 회원 아이디 목록을 생성한다.")
    void construct_usernames() {
        final Set<UserName> usernames = Set.of(new UserName("kth990303"), new UserName("forky"));

        assertThatNoException().isThrownBy(() -> new UserNames(usernames));
    }

    @Test
    @DisplayName("중복된 아이디의 회원을 등록하려 할 경우 예외를 발생시킨다.")
    void duplicate_username() {
        final UserName username = new UserName("abcde");
        final UserNames userNames = new UserNames(Set.of(username));

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> userNames.add(username))
                .withMessageContaining("중복");
    }
}
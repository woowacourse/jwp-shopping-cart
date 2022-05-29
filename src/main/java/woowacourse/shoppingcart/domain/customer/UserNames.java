package woowacourse.shoppingcart.domain.customer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserNames {
    private final Set<UserName> names;

    public UserNames(Set<UserName> names) {
        this.names = new HashSet<>(names);
    }

    public static UserNames from(List<String> names) {
        return new UserNames(names.stream()
                .map(UserName::new)
                .collect(Collectors.toUnmodifiableSet()));
    }

    public void add(String userName) {
        add(new UserName(userName));
    }

    public void add(UserName userName) {
        if (!names.add(userName)) {
            throw new IllegalArgumentException("기존 회원 아이디와 중복되는 아이디입니다.");
        }
    }
}

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

    public boolean contains(String userName) {
        return contains(new UserName(userName));
    }

    public boolean contains(UserName userName) {
        return names.contains(userName);
    }
}

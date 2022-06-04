package woowacourse.shoppingcart.domain.customer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Usernames {
    private final Set<Username> names;

    public Usernames(Set<Username> names) {
        this.names = new HashSet<>(names);
    }

    public static Usernames from(List<String> names) {
        return new Usernames(names.stream()
                .map(Username::new)
                .collect(Collectors.toUnmodifiableSet()));
    }

    public boolean contains(String username) {
        return contains(new Username(username));
    }

    public boolean contains(Username username) {
        return names.contains(username);
    }
}

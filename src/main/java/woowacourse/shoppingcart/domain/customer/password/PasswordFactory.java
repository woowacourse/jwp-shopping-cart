package woowacourse.shoppingcart.domain.customer.password;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

public class PasswordFactory {

    private static final Map<PasswordType, PasswordCreationStrategy> cache;

    static {
        cache = new EnumMap<>(PasswordType.class);
        cache.put(PasswordType.EXISTED, new ExistedPasswordStrategy());
        cache.put(PasswordType.HASHED, new HashedPasswordStrategy());
    }

    public static Password of(final PasswordType type, final String targetPassword) {
        final PasswordCreationStrategy strategy = Optional.ofNullable(cache.get(type))
                .orElseThrow(IllegalArgumentException::new);
        return strategy.createPassword(targetPassword);
    }
}

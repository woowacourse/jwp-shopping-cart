package cart.cart.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Cart {

    private final Long id;
    private final Long memberId;

    public static Cart of(final Long id, final Long memberId) {
        return new Cart(id, memberId);
    }
}

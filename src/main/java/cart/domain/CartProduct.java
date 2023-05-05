package cart.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class CartProduct {
    private final Long id;
    private final Long memberId;
    private final Long productId;

    public CartProduct(Long memberId, Long productId) {
        this(null, memberId, productId);
    }
}

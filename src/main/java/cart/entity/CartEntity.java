package cart.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CartEntity {

    private final long memberId;
    private final long productId;
}

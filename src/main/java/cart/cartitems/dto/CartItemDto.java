package cart.cartitems.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CartItemDto {

    @NotNull
    private final long memberId;
    @NotNull
    private final long productId;

    public CartItemDto(long memberId, long productId) {
        this.memberId = memberId;
        this.productId = productId;
    }
}

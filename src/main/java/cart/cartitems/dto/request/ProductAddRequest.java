package cart.cartitems.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class ProductAddRequest {

    @NotNull
    private Long productId;
}

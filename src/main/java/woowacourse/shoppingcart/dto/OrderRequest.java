package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public class OrderRequest {

    @NotNull
    private final Long cartId;
    @Min(0)
    private final int quantity;
}

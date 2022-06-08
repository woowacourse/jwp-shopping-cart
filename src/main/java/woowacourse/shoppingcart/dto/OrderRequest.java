package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderRequest {

    @NotNull
    private Long cartId;
    @Positive
    private int quantity;
}

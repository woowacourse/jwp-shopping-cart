package cart.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class CartProductRequest {
    @NotNull(message = "[ERROR] 상품 아이디를 입력해주세요.")
    private Long productId;

    public CartProductRequest(long productId) {
        this.productId = productId;
    }
}

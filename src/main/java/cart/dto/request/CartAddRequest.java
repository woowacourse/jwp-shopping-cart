package cart.dto.request;

import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartAddRequest {
    @Positive(message = "카트에 추가할 상품 ID는 0보다 큰 수여야 합니다.")
    private Long productId;
}
